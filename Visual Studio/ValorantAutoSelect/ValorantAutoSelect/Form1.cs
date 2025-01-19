using System;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace ValorantAutoSelect
{
    public partial class Form1 : Form
    {
        private TcpListener server;
        private bool isListening = false;
        private Label statusLabel;
        private TextBox ipTextBox;
        private Button startButton;

        // Mouse kontrolü için Win32 API fonksiyonları
        [DllImport("user32.dll")]
        static extern bool SetCursorPos(int x, int y);

        [DllImport("user32.dll")]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

        public const int MOUSEEVENTF_LEFTDOWN = 0x02;
        public const int MOUSEEVENTF_LEFTUP = 0x04;

        public Form1()
        {
            InitializeComponent();
            InitializeUI();
        }

        private void InitializeUI()
        {
            this.Text = "Valorant Karakter Seçici";
            this.Size = new Size(400, 200);

            statusLabel = new Label
            {
                Text = "Sunucu Durumu: Kapalı",
                Location = new Point(10, 20),
                AutoSize = true
            };

            ipTextBox = new TextBox
            {
                Text = GetLocalIPAddress(),
                Location = new Point(10, 50),
                Width = 200,
                ReadOnly = true
            };

            startButton = new Button
            {
                Text = "Sunucuyu Başlat",
                Location = new Point(10, 80),
                Width = 200
            };
            startButton.Click += StartButton_Click;

            this.Controls.AddRange(new Control[] { statusLabel, ipTextBox, startButton });
        }

        private async void StartButton_Click(object sender, EventArgs e)
        {
            if (!isListening)
            {
                try
                {
                    server = new TcpListener(IPAddress.Any, 12345);
                    server.Start();
                    isListening = true;
                    startButton.Text = "Sunucuyu Durdur";
                    statusLabel.Text = "Sunucu Durumu: Çalışıyor";
                    await ListenForClients();
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Hata: {ex.Message}");
                }
            }
            else
            {
                StopServer();
            }
        }

        private async Task ListenForClients()
        {
            while (isListening)
            {
                try
                {
                    TcpClient client = await server.AcceptTcpClientAsync();
                    _ = HandleClientAsync(client);
                }
                catch (Exception)
                {
                    if (isListening)
                    {
                        MessageBox.Show("Bağlantı hatası oluştu!");
                    }
                }
            }
        }

        private async Task HandleClientAsync(TcpClient client)
        {
            using (NetworkStream stream = client.GetStream())
            using (StreamReader reader = new StreamReader(stream, Encoding.UTF8))
            {
                while (client.Connected)
                {
                    try
                    {
                        string message = await reader.ReadLineAsync();
                        if (message != null)
                        {
                            ProcessCommand(message);
                        }
                    }
                    catch
                    {
                        break;
                    }
                }
            }
        }

        private void ProcessCommand(string command)
        {
            string[] parts = command.Split(':');
            if (parts[0] == "SELECT" && parts.Length == 4)
            {
                string character = parts[1];
                if (int.TryParse(parts[2], out int x) && int.TryParse(parts[3], out int y))
                {
                    this.Invoke((MethodInvoker)delegate
                    {
                        statusLabel.Text = $"Seçilen Karakter: {character}";
                        SelectCharacter(x, y);
                    });
                }
            }
            else if (parts[0] == "LOCK" && parts.Length == 3)
            {
                if (int.TryParse(parts[1], out int x) && int.TryParse(parts[2], out int y))
                {
                    this.Invoke((MethodInvoker)delegate
                    {
                        statusLabel.Text = "Karakter Kilitleniyor";
                        SelectCharacter(x, y);
                    });
                }
            }
        }

        private void SelectCharacter(int x, int y)
        {
            // Mouse'u belirtilen konuma hareket ettir
            SetCursorPos(x, y);

            // Mouse tıklama işlemi
            mouse_event(MOUSEEVENTF_LEFTDOWN, x, y, 0, 0);
            System.Threading.Thread.Sleep(100);
            mouse_event(MOUSEEVENTF_LEFTUP, x, y, 0, 0);
        }

        private void StopServer()
        {
            isListening = false;
            server?.Stop();
            startButton.Text = "Sunucuyu Başlat";
            statusLabel.Text = "Sunucu Durumu: Kapalı";
        }

        private string GetLocalIPAddress()
        {
            var host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (var ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    return ip.ToString();
                }
            }
            return "127.0.0.1";
        }

        protected override void OnFormClosing(FormClosingEventArgs e)
        {
            StopServer();
            base.OnFormClosing(e);
        }
    }
}
