namespace ccmScreenlock
{
    partial class MainForm
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다.
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.lockTimer = new System.Windows.Forms.Timer(this.components);
            this.tbPassword = new System.Windows.Forms.TextBox();
            this.lbPCName = new System.Windows.Forms.Label();
            this.lbWelcome = new System.Windows.Forms.Label();
            this.tbID = new System.Windows.Forms.TextBox();
            this.pnLogin = new System.Windows.Forms.Panel();
            this.pnOTP = new System.Windows.Forms.Panel();
            this.pnQR = new System.Windows.Forms.Panel();
            this.pnFinger = new System.Windows.Forms.Panel();
            this.pnOtpInput = new System.Windows.Forms.Panel();
            this.tbOTP = new System.Windows.Forms.TextBox();
            this.pnOtpInput.SuspendLayout();
            this.SuspendLayout();
            // 
            // timer
            // 
            this.timer.Interval = 500;
            // 
            // lockTimer
            // 
            this.lockTimer.Interval = 2000;
            // 
            // tbPassword
            // 
            this.tbPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.tbPassword.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.tbPassword.Font = new System.Drawing.Font("나눔스퀘어", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.tbPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(140)))), ((int)(((byte)(140)))), ((int)(((byte)(140)))));
            this.tbPassword.Location = new System.Drawing.Point(1153, 579);
            this.tbPassword.Name = "tbPassword";
            this.tbPassword.PasswordChar = '●';
            this.tbPassword.Size = new System.Drawing.Size(340, 22);
            this.tbPassword.TabIndex = 2;
            // 
            // lbPCName
            // 
            this.lbPCName.AutoSize = true;
            this.lbPCName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.lbPCName.Font = new System.Drawing.Font("나눔스퀘어 ExtraBold", 27.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.lbPCName.ForeColor = System.Drawing.Color.White;
            this.lbPCName.Location = new System.Drawing.Point(1118, 224);
            this.lbPCName.Name = "lbPCName";
            this.lbPCName.Size = new System.Drawing.Size(210, 40);
            this.lbPCName.TabIndex = 7;
            this.lbPCName.Text = "누군가의 PC";
            // 
            // lbWelcome
            // 
            this.lbWelcome.AutoSize = true;
            this.lbWelcome.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.lbWelcome.Font = new System.Drawing.Font("나눔스퀘어 Bold", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.lbWelcome.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(124)))), ((int)(((byte)(124)))), ((int)(((byte)(124)))));
            this.lbWelcome.Location = new System.Drawing.Point(1121, 285);
            this.lbWelcome.Name = "lbWelcome";
            this.lbWelcome.Size = new System.Drawing.Size(286, 21);
            this.lbWelcome.TabIndex = 8;
            this.lbWelcome.Text = "원하는 방법을 선택해 로그인하세요.";
            // 
            // tbID
            // 
            this.tbID.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.tbID.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.tbID.Font = new System.Drawing.Font("나눔스퀘어", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.tbID.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(140)))), ((int)(((byte)(140)))), ((int)(((byte)(140)))));
            this.tbID.Location = new System.Drawing.Point(1153, 442);
            this.tbID.Name = "tbID";
            this.tbID.ReadOnly = true;
            this.tbID.Size = new System.Drawing.Size(340, 22);
            this.tbID.TabIndex = 15;
            // 
            // pnLogin
            // 
            this.pnLogin.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.pnLogin.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnLogin.BackgroundImage")));
            this.pnLogin.Cursor = System.Windows.Forms.Cursors.Hand;
            this.pnLogin.Location = new System.Drawing.Point(1130, 827);
            this.pnLogin.Name = "pnLogin";
            this.pnLogin.Size = new System.Drawing.Size(580, 65);
            this.pnLogin.TabIndex = 16;
            this.pnLogin.Click += new System.EventHandler(this.panel1_Click);
            // 
            // pnOTP
            // 
            this.pnOTP.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.pnOTP.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnOTP.BackgroundImage")));
            this.pnOTP.Cursor = System.Windows.Forms.Cursors.Hand;
            this.pnOTP.Location = new System.Drawing.Point(1129, 692);
            this.pnOTP.Name = "pnOTP";
            this.pnOTP.Size = new System.Drawing.Size(186, 65);
            this.pnOTP.TabIndex = 17;
            this.pnOTP.Click += new System.EventHandler(this.pnOTP_Click);
            // 
            // pnQR
            // 
            this.pnQR.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.pnQR.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnQR.BackgroundImage")));
            this.pnQR.Cursor = System.Windows.Forms.Cursors.Hand;
            this.pnQR.Location = new System.Drawing.Point(1327, 692);
            this.pnQR.Name = "pnQR";
            this.pnQR.Size = new System.Drawing.Size(186, 65);
            this.pnQR.TabIndex = 18;
            this.pnQR.Click += new System.EventHandler(this.pnQR_Click);
            // 
            // pnFinger
            // 
            this.pnFinger.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.pnFinger.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnFinger.BackgroundImage")));
            this.pnFinger.Cursor = System.Windows.Forms.Cursors.Hand;
            this.pnFinger.Location = new System.Drawing.Point(1524, 692);
            this.pnFinger.Name = "pnFinger";
            this.pnFinger.Size = new System.Drawing.Size(186, 65);
            this.pnFinger.TabIndex = 19;
            this.pnFinger.Click += new System.EventHandler(this.pnFinger_Click);
            // 
            // pnOtpInput
            // 
            this.pnOtpInput.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.pnOtpInput.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnOtpInput.BackgroundImage")));
            this.pnOtpInput.Controls.Add(this.tbOTP);
            this.pnOtpInput.Location = new System.Drawing.Point(1129, 771);
            this.pnOtpInput.Name = "pnOtpInput";
            this.pnOtpInput.Size = new System.Drawing.Size(580, 65);
            this.pnOtpInput.TabIndex = 20;
            this.pnOtpInput.Visible = false;
            // 
            // tbOTP
            // 
            this.tbOTP.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(27)))), ((int)(((byte)(28)))));
            this.tbOTP.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.tbOTP.Enabled = false;
            this.tbOTP.Font = new System.Drawing.Font("나눔스퀘어", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.tbOTP.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(140)))), ((int)(((byte)(140)))), ((int)(((byte)(140)))));
            this.tbOTP.Location = new System.Drawing.Point(24, 23);
            this.tbOTP.Name = "tbOTP";
            this.tbOTP.Size = new System.Drawing.Size(340, 22);
            this.tbOTP.TabIndex = 21;
            // 
            // MainForm
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(249)))), ((int)(((byte)(249)))), ((int)(((byte)(249)))));
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.ClientSize = new System.Drawing.Size(1920, 1080);
            this.Controls.Add(this.pnOtpInput);
            this.Controls.Add(this.pnFinger);
            this.Controls.Add(this.pnQR);
            this.Controls.Add(this.pnOTP);
            this.Controls.Add(this.pnLogin);
            this.Controls.Add(this.tbID);
            this.Controls.Add(this.lbWelcome);
            this.Controls.Add(this.lbPCName);
            this.Controls.Add(this.tbPassword);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "MainForm";
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.TopMost = true;
            this.pnOtpInput.ResumeLayout(false);
            this.pnOtpInput.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Timer timer;
        private System.Windows.Forms.Timer lockTimer;
        private System.Windows.Forms.TextBox tbPassword;
        private System.Windows.Forms.Label lbPCName;
        private System.Windows.Forms.Label lbWelcome;
        private System.Windows.Forms.TextBox tbID;
        private System.Windows.Forms.Panel pnLogin;
        private System.Windows.Forms.Panel pnOTP;
        private System.Windows.Forms.Panel pnQR;
        private System.Windows.Forms.Panel pnFinger;
        private System.Windows.Forms.Panel pnOtpInput;
        private System.Windows.Forms.TextBox tbOTP;
    }
}

