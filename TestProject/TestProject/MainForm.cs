using Microsoft.Win32;
using System;
using System.IO;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

using RestSharp;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace ccmScreenlock
{
    public partial class MainForm : Form
    {

        private string userid = null;
        private string password = null;
        private string name = null;

        private auth sel_auth = 0;
        private string otp_code = null;

        enum auth { Otp = 1, Qr, Finger };

        //////////////////////////////////////////////////////////////////////////////////////////////////// Structure
        ////////////////////////////////////////////////////////////////////////////////////////// Public

        #region 키보드 저수준 후킹 구조체 - KBDLLHOOKSTRUCT

        /// <summary>
        /// 키보드 저수준 후킹 구조체
        /// </summary>
        public struct KBDLLHOOKSTRUCT
        {
            //////////////////////////////////////////////////////////////////////////////////////////////////// Field
            ////////////////////////////////////////////////////////////////////////////////////////// Public

            #region Field

            /// <summary>
            /// 가상 키 코드
            /// </summary>
            public int VirtualKeyCode;

            /// <summary>
            /// 스캔 코드
            /// </summary>
            public int ScanCode;

            /// <summary>
            /// 플래그
            /// </summary>
            public int Flags;

            /// <summary>
            /// 시간
            /// </summary>
            public int Time;

            /// <summary>
            /// 부가 정보
            /// </summary>
            public int ExtraInfo;

            #endregion
        }

        #endregion

        //////////////////////////////////////////////////////////////////////////////////////////////////// Delegate
        ////////////////////////////////////////////////////////////////////////////////////////// Private

        #region 키보드 후킹 처리 대리자 - ProcessKeyboardHookDelegate(code, wordParameter, longParameter)

        /// <summary>
        /// 키보드 후킹 처리 대리자
        /// </summary>
        /// <param name="code">코드</param>
        /// <param name="wordParameter">WORD 매개 변수</param>
        /// <param name="longParameter">LONG 매개 변수</param>
        /// <returns>처리 결과</returns>
        private delegate int ProcessKeyboardHookDelegate(int code, int wordParameter, ref KBDLLHOOKSTRUCT longParameter);

        #endregion

        //////////////////////////////////////////////////////////////////////////////////////////////////// Import
        ////////////////////////////////////////////////////////////////////////////////////////// Static
        //////////////////////////////////////////////////////////////////////////////// Private

        #region 윈도우 후킹 설정하기 - SetWindowsHookEx(hookID, processKeyboardHookDelegate, moduleHandle, threadID)

        /// <summary>
        /// 윈도우 후킹 설정하기
        /// </summary>
        /// <param name="hookID">후킹 ID</param>
        /// <param name="processKeyboardHookDelegate">키보드 후킹 처리 대리자</param>
        /// <param name="moduleHandle">모듈 핸들</param>
        /// <param name="threadID">스레드 ID</param>
        /// <returns>처리 결과</returns>
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int SetWindowsHookEx(int hookID, ProcessKeyboardHookDelegate processKeyboardHookDelegate, IntPtr moduleHandle, uint threadID);

        #endregion
        #region 윈도우 후킹 해제하기 - UnhookWindowsHookEx(hookHandle)

        /// <summary>
        /// 윈도우 후킹 해제하기
        /// </summary>
        /// <param name="hookHandle">후킹 핸들</param>
        /// <returns>처리 결과</returns>
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(int hookHandle);

        #endregion
        #region 다음 후킹 호출하기 - CallNextHookEx(hookHandle, code, wordParameter, longParameter)

        /// <summary>
        /// 다음 후킹 호출하기
        /// </summary>
        /// <param name="hookHandle">후킹 핸들</param>
        /// <param name="code">코드</param>
        /// <param name="wordParameter">WORD 매개 변수</param>
        /// <param name="longParameter">LONG 매개 변수</param>
        /// <returns>처리 결과</returns>
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int CallNextHookEx(int hookHandle, int code, int wordParameter, ref KBDLLHOOKSTRUCT longParameter);

        #endregion
        #region 모듈 핸들 구하기 - GetModuleHandle(modulName)

        /// <summary>
        /// 모듈 핸들 구하기
        /// </summary>
        /// <param name="modulName">모듈명</param>
        /// <returns>모듈 핸들</returns>
        [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string modulName);

        #endregion

        //////////////////////////////////////////////////////////////////////////////////////////////////// Field
        ////////////////////////////////////////////////////////////////////////////////////////// Static
        //////////////////////////////////////////////////////////////////////////////// Private

        #region Field

        /// <summary>
        /// 키보드 후킹 처리 대리자
        /// </summary>
        private static ProcessKeyboardHookDelegate _processKeyboardHookDelegate = ProcessKeyboardHook;

        /// <summary>
        /// 후킹 ID
        /// </summary>
        private static int _hookID = 0;

        #endregion

        ////////////////////////////////////////////////////////////////////////////////////////// Instance
        //////////////////////////////////////////////////////////////////////////////// Private

        #region Field

        /// <summary>
        /// WH_KEYBOARD_LL
        /// </summary>
        private const int WH_KEYBOARD_LL = 13;

        /// <summary>
        /// WM_KEYDOWN
        /// </summary>
        private const int WM_KEYDOWN = 0x0100;

        /// <summary>
        /// WM_KEYUP
        /// </summary>
        private const int WM_KEYUP = 0x0101;

        /// <summary>
        /// WM_SYSKEYDOWN
        /// </summary>
        private const int WM_SYSKEYDOWN = 0x0104;

        /// <summary>
        /// WM_SYSKEYUP
        /// </summary>
        private const int WM_SYSKEYUP = 0x0105;


        /// <summary>
        /// 화면 사각형
        /// </summary>
        private Rectangle screenRectangle = Screen.PrimaryScreen.Bounds;

        /// <summary>
        /// 마우스 X 좌표
        /// </summary>
        private int mouseX = 0;

        /// <summary>
        /// 마우스 Y 좌표
        /// </summary>
        private int mouseY = 0;

        /// <summary>
        /// 스크린 세이버 중단 가능 여부
        /// </summary>
        private bool canStopScreenSaver = true;

        /// <summary>
        /// 난수 발생기
        /// </summary>
        private Random random = new Random();

        #endregion

        //////////////////////////////////////////////////////////////////////////////////////////////////// Constructor
        ////////////////////////////////////////////////////////////////////////////////////////// Public

        // 생성자 - MainForm()
        public MainForm()  
        {
            InitializeComponent();

            Load                += Form_Load;
        }
                
        //////////////////////////////////////////////////////////////////////////////////////////////////// Method
        ////////////////////////////////////////////////////////////////////////////////////////// Static
        //////////////////////////////////////////////////////////////////////////////// Private
        ////////////////////////////////////////////////////////////////////// Function

        #region 키보드 후킹 처리하기 - ProcessKeyboardHook(code, wordParameter, longParameter)

        /// <summary>
        /// 키보드 후킹 처리하기
        /// </summary>
        /// <param name="code">코드</param>
        /// <param name="wordParameter">WORD 매개 변수</param>
        /// <param name="longParameter">LONG 매개 변수</param>
        /// <returns>처리 결과</returns>
        private static int ProcessKeyboardHook(int code, int wordParameter, ref KBDLLHOOKSTRUCT longParameter)
        {
            bool result = false;

            switch(wordParameter)
            {
                case WM_KEYDOWN    :
                case WM_KEYUP      :
                case WM_SYSKEYDOWN :
                case WM_SYSKEYUP   :

                    result = ((longParameter.VirtualKeyCode == 0x09) && (longParameter.Flags == 0x20)) || // Alt + Tab
                             ((longParameter.VirtualKeyCode == 0x1B) && (longParameter.Flags == 0x20)) || // Alt + Esc
                             ((longParameter.VirtualKeyCode == 0x1B) && (longParameter.Flags == 0x00)) || // Ctrl + Esc
                             ((longParameter.VirtualKeyCode == 0x5B) && (longParameter.Flags == 0x01)) || // Left Windows Key
                             ((longParameter.VirtualKeyCode == 0x5C) && (longParameter.Flags == 0x01)) || // Right Windows Key
                             ((longParameter.VirtualKeyCode == 0x73) && (longParameter.Flags == 0x20));   // Alt + F4

                    break;
            }

            if(result == true)
            {
                return 1;
            }
            else
            {
                return CallNextHookEx(0, code, wordParameter, ref longParameter);
            }
        }

        #endregion

        ////////////////////////////////////////////////////////////////////////////////////////// Instance
        //////////////////////////////////////////////////////////////////////////////// Private
        ////////////////////////////////////////////////////////////////////// Event


        /// <summary>
        /// 폼 로드시 처리하기
        /// </summary>
        /// <param name="sender">이벤트 발생자</param>
        /// <param name="e">이벤트 인자</param>
        private void Form_Load(object sender, EventArgs e)
        {
            this.timer.Enabled     = true;
            this.lockTimer.Enabled = true;

            this.canStopScreenSaver = true;

            DisableTaskManager();
            _hookID = SetHook(_processKeyboardHookDelegate);

            // ini 파일에서 정보 가져오기
            FileInfo fileInfo = new FileInfo(@"config.ini");

            if (fileInfo.Exists == true)
            {
                StreamReader streamReader = File.OpenText(@"config.ini");

                if (streamReader != null)
                {
                    userid =  streamReader.ReadLine();
                }

                streamReader.Close();
            }
            else
            {
                Hide();
            }

            // 값 가져오기
            
            string url = "http://localhost:8080/cocomo/users/";
            url += userid;
            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);

            IRestResponse response = client.Execute(request);

            // json 형태로
            var jObject = JObject.Parse(response.Content);

            name = jObject.GetValue("userName").ToString();
            userid = jObject.GetValue("userId").ToString();
            password = jObject.GetValue("passwd").ToString();

            /*var test = name + "/" + userid + "/" + password;
            MessageBox.Show(test);*/

            // 버튼 외곽 제거
            //btnOTP.FlatStyle = FlatStyle.Flat;
            //btnOTP.FlatAppearance.BorderSize = 0;

            // 배경색 투명하게
            lbPCName.BackColor = Color.Transparent;
            lbPCName.Parent = this;
            lbWelcome.BackColor = Color.Transparent;
            lbWelcome.Parent = this;

            pnLogin.BackColor = Color.Transparent;
            pnLogin.Parent = this;
            pnOTP.BackColor = Color.Transparent;
            pnOTP.Parent = this;
            pnQR.BackColor = Color.Transparent;
            pnQR.Parent = this;
            pnFinger.BackColor = Color.Transparent;
            pnFinger.Parent = this; 
            pnOtpInput.BackColor = Color.Transparent;
            pnOtpInput.Parent = this;

            // label 값 세팅
            lbPCName.Text = name + "의 pc";
            tbID.Text = userid;
        }

        #region 태스크 관리자 비활성화 하기 - DisableTaskManager()

        /// <summary>
        /// 태스크 관리자 비활성화 하기
        /// </summary>
        /// <remarks>Ctrl + Alt + Delete 키 입력을 거부한다.</remarks>
        public static void DisableTaskManager()
        {
            RegistryKey registryKey;
            int         keyValue = 1;
            string      subKey   = "Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System";

            try
            {
                registryKey = Registry.CurrentUser.CreateSubKey(subKey);

                registryKey.SetValue("DisableTaskMgr", keyValue);

                registryKey.Close();
            }
            catch(Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        #endregion
        #region 태스크 관리자 활성화 하기 - EnableTaskManager()

        /// <summary>
        /// 태스크 관리자 활성화 하기
        /// </summary>
        /// <remarks>Ctrl + Alt + Delete 키 입력을 허용한다.</remarks>
        public static void EnableTaskManager()
        {
            try
            {
                RegistryKey currentUserRegistryKey = Registry.CurrentUser;
                string      subKey                 = "Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System";
                RegistryKey registryKey            = currentUserRegistryKey.OpenSubKey(subKey);

                if(registryKey != null)
                {
                    currentUserRegistryKey.DeleteSubKeyTree(subKey);
                }
            }
            catch(Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        #endregion
        #region 후킹 설정하기 - SetHook(processKeyboardHookDelegate)

        /// <summary>
        /// 후킹 설정하기
        /// </summary>
        /// <param name="processKeyboardHookDelegate">키보드 후킹 처리 대리자</param>
        /// <returns>처리 결과</returns>
        private static int SetHook(ProcessKeyboardHookDelegate processKeyboardHookDelegate)
        {
            using(Process process = Process.GetCurrentProcess())
            {
                using(ProcessModule processModule = process.MainModule)
                {
                    return SetWindowsHookEx(WH_KEYBOARD_LL, processKeyboardHookDelegate, GetModuleHandle(processModule.ModuleName), 0);
                }
            }
        }

        #endregion


        // 비상 버튼. 나중에 지울 것
        private void btnRun_Click(object sender, EventArgs e)
        {
            Cursor.Show();

            this.timer.Enabled = false;

            UnhookWindowsHookEx(_hookID);
            EnableTaskManager();

            Application.Exit();
        }

        private void panel1_Click(object sender, EventArgs e)
        {
            switch (sel_auth)
            {
                case auth.Otp:
                    string inputCode = tbOTP.Text.Trim();

                    string url = "http://localhost:8080/auth/otp/";
                    url += userid;
                    var client = new RestClient(url);
                    var request = new RestRequest(Method.GET);

                    IRestResponse response = client.Execute(request);

                    // json 형태로
                    var jObject = JObject.Parse(response.Content);
                    otp_code = jObject.GetValue("otpCode").ToString();

                    if (password != tbPassword.Text.Trim())
                    {
                        MessageBox.Show("잘못된 비밀번호입니다.");
                        return;
                    }
                    else if (inputCode != otp_code)
                    {
                        MessageBox.Show("잘못된 OTP 코드입니다.");
                        return;
                    }

                    HideScreenlock();

                    break;

                case auth.Qr:
                    MessageBox.Show("QR 스캐너로 QR을 스캔해주세요.");
                    break;

                case auth.Finger:
                    MessageBox.Show("앱을 통해 지문 인증을 진행해주세요.");
                    break;

                default:
                    MessageBox.Show("인증 방식을 선택하여 추가 인증을 진행하세요.");
                    break;
            }

            return;
        }

        private void pnOTP_Click(object sender, EventArgs e)
        {
            pnOTP.BackgroundImage = TestProject.Properties.Resources.screen_auth_otpbtn_on;
            pnQR.BackgroundImage = TestProject.Properties.Resources.screen_auth_qrbtn_off;
            pnFinger.BackgroundImage = TestProject.Properties.Resources.screen_auth_fingerbtn_off;
            pnOtpInput.Visible = true;
            tbOTP.Enabled = true;
            pnLogin.Location = new Point(1130, 882);

            sel_auth = auth.Otp;
        }

        private async void pnQR_Click(object sender, EventArgs e)
        {
            pnOTP.BackgroundImage = TestProject.Properties.Resources.screen_auth_otpbtn_off;
            pnQR.BackgroundImage = TestProject.Properties.Resources.screen_auth_qrbtn_on;
            pnFinger.BackgroundImage = TestProject.Properties.Resources.screen_auth_fingerbtn_off;
            pnOtpInput.Visible = false;
            tbOTP.Enabled = false;
            pnLogin.Location = new Point(1130, 827);

            sel_auth = auth.Qr;

            string url = "http://localhost:8080/auth/qr-create/";
            url += userid;
            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            TestProject.QRForm frm = new TestProject.QRForm();
            frm.Show();
            frm.Location = new Point(800, 410);
            frm.TopMost = true;

            string auth_res;
            auth_res = await SendAuthQr();

            frm.Close();

            if (password != tbPassword.Text.Trim())
            {
                MessageBox.Show("잘못된 비밀번호입니다.");
                return;
            }
            else if (auth_res == "777")
            {
                HideScreenlock();
            }
            else
            {
                MessageBox.Show("인증에 실패하였습니다.");
                return;
            }
        }

        private async void pnFinger_Click(object sender, EventArgs e)
        {
            pnOTP.BackgroundImage = TestProject.Properties.Resources.screen_auth_otpbtn_off;
            pnQR.BackgroundImage = TestProject.Properties.Resources.screen_auth_qrbtn_off;
            pnFinger.BackgroundImage = TestProject.Properties.Resources.screen_auth_fingerbtn_on;
            pnOtpInput.Visible = false;
            tbOTP.Enabled = false;
            pnLogin.Location = new Point(1130, 827);

            sel_auth = auth.Finger;

            string auth_res;
            auth_res = await SendAuthFinger();

            if (password != tbPassword.Text.Trim())
            {
                MessageBox.Show("잘못된 비밀번호입니다.");
                return;
            }
            else if (auth_res == "777")
            {
                HideScreenlock();
            }
            else
            {
                MessageBox.Show("인증에 실패하였습니다.");
                return;
            }

        }

        private async Task<string> SendAuthQr()
        {
            string url = "http://localhost:8080/auth/qr/";
            url += userid;
            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);

            var res = await client.ExecuteAsync(request);

            string result = res.Content.ToString();

            //MessageBox.Show(result);

            return result;
        }

        private async Task<string> SendAuthFinger()
        {
            string url = "http://localhost:8080/auth/finger/";
            url += userid;
            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);

            var res = await client.ExecuteAsync(request);
            
            string result = res.Content.ToString();

            return result;
        }

        private async Task<string> SendLockRequest()
        {
            string url = "http://localhost:8080/auth/lock/";
            url += userid;
            var client = new RestClient(url);
            var request = new RestRequest(Method.GET);

            var res = await client.ExecuteAsync(request);

            // json 형태로
            var jObject = JObject.Parse(res.Content);
            string lock_exe;
            lock_exe = jObject.GetValue("executeScreenlock").ToString();

            return lock_exe;
        }

        private void HideScreenlock()
        {
            Cursor.Show();
            this.timer.Enabled = false;
            UnhookWindowsHookEx(_hookID);
            EnableTaskManager();

            this.ShowInTaskbar = false;
            this.Opacity = 0;

            WaitLockRequest();
        }

        private async Task WaitLockRequest()
        {
            string result;
            result = await SendLockRequest();

            if (result == "1")
            {
                this.Opacity = 100;
                this.ShowInTaskbar = true;
                //_hookID = SetHook(_processKeyboardHookDelegate);
                //DisableTaskManager();
            }

        }
    }
}