using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TestProject
{
    public partial class QRForm : Form
    {
        public QRForm()
        {
            InitializeComponent();
        }

        private void QRForm_Load(object sender, EventArgs e)
        {
            pbQR.Load(@"D:\@project\@source\rest-server\rest-server\target\classes\cocomo\restserver\auth\qr\cocomoQR0.png");
            //pbQR.SizeMode = PictureBoxSizeMode.StretchImage;
        }
    }
}
