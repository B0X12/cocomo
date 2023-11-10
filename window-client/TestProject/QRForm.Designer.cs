namespace TestProject
{
    partial class QRForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.pbQR = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pbQR)).BeginInit();
            this.SuspendLayout();
            // 
            // pbQR
            // 
            this.pbQR.Location = new System.Drawing.Point(-1, 0);
            this.pbQR.Name = "pbQR";
            this.pbQR.Size = new System.Drawing.Size(200, 200);
            this.pbQR.TabIndex = 0;
            this.pbQR.TabStop = false;
            // 
            // QRForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(198, 200);
            this.Controls.Add(this.pbQR);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "QRForm";
            this.Text = "QRForm";
            this.Load += new System.EventHandler(this.QRForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pbQR)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox pbQR;
    }
}