
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrevitechApp extends JFrame {

    private final Color AZUL_ESCURO = new Color(24, 31, 88);
    private final Color AZUL_MENU = new Color(28, 36, 100);
    private final Color AZUL = new Color(39, 105, 190);
    private final Color AZUL_CLARO = new Color(230, 242, 255);
    private final Color CIANO = new Color(16, 176, 224);
    private final Color VERDE = new Color(18, 150, 100);
    private final Color LARANJA = new Color(245, 145, 0);
    private final Color VERMELHO = new Color(214, 68, 68);
    private final Color CINZA_TEXTO = new Color(75, 85, 99);
    private final Color CINZA_BORDA = new Color(215, 224, 235);
    private final Color FUNDO = new Color(247, 249, 252);

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private DefaultTableModel modeloAcompanhamento;
    private DefaultTableModel modeloGerenciamento;
    private int proximoIdChamado = 78;

    private String usuarioLogado = "Gustavo Braga";
    private String perfilLogado = "Administrador";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrevitechApp().setVisible(true));
    }

    public PrevitechApp() {
        setTitle("PREVITECH - Sistema de Manutencao TI");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        iniciarModelos();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(telaInicial(), "inicio");
        mainPanel.add(telaLogin(), "login");
        mainPanel.add(telaCadastro(), "cadastro");
        mainPanel.add(telaDashboard(), "dashboard");
        mainPanel.add(telaChamado(), "chamado");
        mainPanel.add(telaAcompanhamento(), "acompanhamento");
        mainPanel.add(telaManutencao(), "manutencao");
        mainPanel.add(telaEquipamento(), "equipamento");
        mainPanel.add(telaAdministrador(), "administrador");
        mainPanel.add(telaEstoque(), "estoque");
        mainPanel.add(telaGerenciamento(), "gerenciamento");
        mainPanel.add(telaSobre(), "sobre");
        mainPanel.add(telaPerfil(), "perfil");

        add(mainPanel);
        cardLayout.show(mainPanel, "inicio");
    }

    private void iniciarModelos() {
        modeloAcompanhamento = new DefaultTableModel(
                new Object[] { "Equipamentos", "ID", "Data", "Status" }, 0);

        modeloAcompanhamento.addRow(new Object[] { "Impressora Lazer HP", "075", "22/04", "EM ANDAMENTO" });
        modeloAcompanhamento.addRow(new Object[] { "Servidor Rack 1U", "076", "22/04", "ABERTO" });
        modeloAcompanhamento.addRow(new Object[] { "Impressora Lazer Epson", "077", "22/04", "FINALIZADO" });

        modeloGerenciamento = new DefaultTableModel(
                new Object[] { "Usuario/ID", "Problema", "Status", "Acao" }, 0);

        modeloGerenciamento
                .addRow(new Object[] { "Gustavo Braga", "Impressora Lazer HP", "ABERTO", "EDITAR/FINALIZAR" });
        modeloGerenciamento.addRow(new Object[] { "Gustavo Braga", "Servidor Rack 1U", "ABERTO", "EDITAR/FINALIZAR" });
        modeloGerenciamento
                .addRow(new Object[] { "Gustavo Braga", "Impressora Lazer Epson", "FINALIZADO", "EDITAR/FINALIZAR" });
    }

    static class RoundPanel extends JPanel {
        private int radius;
        private Color bg;

        public RoundPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private JButton botao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel titulo(String texto, int tamanho) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, tamanho));
        label.setForeground(new Color(20, 24, 36));
        return label;
    }

    private JTextField campoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(new CompoundBorder(
                new LineBorder(CINZA_BORDA),
                new EmptyBorder(8, 10, 8, 10)));
        campo.setToolTipText(placeholder);
        return campo;
    }

    private JPasswordField campoSenha(String placeholder) {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(new CompoundBorder(
                new LineBorder(CINZA_BORDA),
                new EmptyBorder(8, 10, 8, 10)));
        campo.setToolTipText(placeholder);
        return campo;
    }

    private JPanel telaInicial() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(AZUL_ESCURO);

        RoundPanel card = new RoundPanel(28, Color.WHITE);
        card.setPreferredSize(new Dimension(760, 460));
        card.setLayout(null);

        JLabel logoBox = new JLabel("PV", SwingConstants.CENTER);
        logoBox.setOpaque(true);
        logoBox.setBackground(AZUL_ESCURO);
        logoBox.setForeground(Color.WHITE);
        logoBox.setFont(new Font("Arial", Font.BOLD, 22));
        logoBox.setBounds(335, 38, 90, 55);
        card.add(logoBox);

        JLabel logo = new JLabel("PREVITECH", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 42));
        logo.setForeground(AZUL_ESCURO);
        logo.setBounds(0, 105, 760, 55);
        card.add(logo);

        JLabel subtitulo = new JLabel("Sistema de Manutencao Preventiva de TI", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 21));
        subtitulo.setForeground(AZUL);
        subtitulo.setBounds(0, 165, 760, 35);
        card.add(subtitulo);

        JTextArea descricao = new JTextArea(
                "Controle chamados tecnicos, acompanhe manutencoes, cadastre equipamentos e gerencie o estoque de pecas de forma simples, visual e organizada.");
        descricao.setFont(new Font("Arial", Font.PLAIN, 17));
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setEditable(false);
        descricao.setBackground(Color.WHITE);
        descricao.setForeground(CINZA_TEXTO);
        descricao.setBounds(105, 225, 550, 75);
        card.add(descricao);

        JButton entrar = botao("Entrar no Sistema", AZUL);
        entrar.setBounds(230, 320, 300, 45);
        entrar.setFont(new Font("Arial", Font.BOLD, 16));
        entrar.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        card.add(entrar);

        JLabel rodape = new JLabel("Projeto academico desenvolvido em Java Swing", SwingConstants.CENTER);
        rodape.setFont(new Font("Arial", Font.PLAIN, 13));
        rodape.setForeground(Color.GRAY);
        rodape.setBounds(0, 395, 760, 25);
        card.add(rodape);

        fundo.add(card);
        return fundo;
    }

    private JPanel telaLogin() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(AZUL_ESCURO);

        RoundPanel card = new RoundPanel(22, Color.WHITE);
        card.setPreferredSize(new Dimension(470, 440));
        card.setLayout(null);

        JLabel title = titulo("Login", 31);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 40, 470, 40);
        card.add(title);

        JLabel info = new JLabel("Admin: admin@previtech.com / admin  |  Usuario: usuario@previtech.com / 123",
                SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setForeground(CINZA_TEXTO);
        info.setBounds(0, 78, 470, 25);
        card.add(info);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(60, 120, 350, 25);
        card.add(emailLabel);

        JTextField email = campoTexto("Email");
        email.setBounds(60, 147, 350, 38);
        card.add(email);

        JLabel senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        senhaLabel.setBounds(60, 195, 350, 25);
        card.add(senhaLabel);

        JPasswordField senha = campoSenha("Senha");
        senha.setBounds(60, 222, 350, 38);
        card.add(senha);

        JLabel tipoLabel = new JLabel("Tipo de acesso");
        tipoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tipoLabel.setBounds(60, 270, 350, 25);
        card.add(tipoLabel);

        String[] tipos = { "Usuario comum", "Administrador" };
        JComboBox<String> tipoAcesso = new JComboBox<>(tipos);
        tipoAcesso.setBounds(60, 297, 350, 35);
        tipoAcesso.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(tipoAcesso);

        JButton entrar = botao("Entrar", AZUL);
        entrar.setBounds(60, 350, 350, 38);
        entrar.addActionListener(e -> {
            String emailDigitado = email.getText().trim();
            String senhaDigitada = new String(senha.getPassword()).trim();
            String tipoSelecionado = tipoAcesso.getSelectedItem().toString();

            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha email e senha para entrar.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean adminOk = emailDigitado.equals("admin@previtech.com") && senhaDigitada.equals("admin")
                    && tipoSelecionado.equals("Administrador");
            boolean usuarioOk = emailDigitado.equals("usuario@previtech.com") && senhaDigitada.equals("123")
                    && tipoSelecionado.equals("Usuario comum");

            if (adminOk) {
                usuarioLogado = "Gustavo Braga";
                perfilLogado = "Administrador";
                JOptionPane.showMessageDialog(this, "Login realizado como Administrador.");
                cardLayout.show(mainPanel, "administrador");
            } else if (usuarioOk) {
                usuarioLogado = "Usuario Comum";
                perfilLogado = "Usuario comum";
                JOptionPane.showMessageDialog(this, "Login realizado como Usuario comum.");
                cardLayout.show(mainPanel, "dashboard");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Email, senha ou tipo de acesso invalidos.\n\n"
                                + "Administrador: admin@previtech.com / admin\n"
                                + "Usuario comum: usuario@previtech.com / 123",
                        "Erro no login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(entrar);

        JButton criarConta = new JButton("Criar conta");
        criarConta.setBounds(160, 390, 150, 25);
        criarConta.setBorderPainted(false);
        criarConta.setContentAreaFilled(false);
        criarConta.setForeground(AZUL);
        criarConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        criarConta.addActionListener(e -> cardLayout.show(mainPanel, "cadastro"));
        card.add(criarConta);

        fundo.add(card);
        return fundo;
    }

    private JPanel telaCadastro() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(AZUL_ESCURO);

        RoundPanel card = new RoundPanel(22, Color.WHITE);
        card.setPreferredSize(new Dimension(500, 500));
        card.setLayout(null);

        JLabel title = titulo("Cadastro", 31);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 35, 500, 40);
        card.add(title);

        JLabel info = new JLabel("Crie sua conta para acessar o sistema", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setForeground(CINZA_TEXTO);
        info.setBounds(0, 75, 500, 25);
        card.add(info);

        int x = 65;
        int largura = 370;

        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nomeLabel.setBounds(x, 120, largura, 25);
        card.add(nomeLabel);

        JTextField nome = campoTexto("Nome");
        nome.setBounds(x, 147, largura, 38);
        card.add(nome);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(x, 195, largura, 25);
        card.add(emailLabel);

        JTextField email = campoTexto("Email");
        email.setBounds(x, 222, largura, 38);
        card.add(email);

        JLabel senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        senhaLabel.setBounds(x, 270, largura, 25);
        card.add(senhaLabel);

        JPasswordField senha = campoSenha("Senha");
        senha.setBounds(x, 297, largura, 38);
        card.add(senha);

        JLabel confirmarLabel = new JLabel("Confirmar senha");
        confirmarLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmarLabel.setBounds(x, 345, largura, 25);
        card.add(confirmarLabel);

        JPasswordField confirmar = campoSenha("Confirmar senha");
        confirmar.setBounds(x, 372, largura, 38);
        card.add(confirmar);

        JButton cadastrar = botao("Cadastrar", AZUL);
        cadastrar.setBounds(x, 430, largura, 38);
        cadastrar.addActionListener(e -> {
            if (nome.getText().trim().isEmpty() || email.getText().trim().isEmpty() ||
                    new String(senha.getPassword()).trim().isEmpty() ||
                    new String(confirmar.getPassword()).trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!new String(senha.getPassword()).equals(new String(confirmar.getPassword()))) {
                JOptionPane.showMessageDialog(this, "As senhas nao conferem.", "Atencao", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario cadastrado com sucesso!");
                cardLayout.show(mainPanel, "login");
            }
        });
        card.add(cadastrar);

        fundo.add(card);
        return fundo;
    }

    private JPanel baseComMenu(String telaAtual, JPanel conteudo) {
        JPanel base = new JPanel(new BorderLayout());
        base.setBackground(FUNDO);

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(225, 750));
        menu.setBackground(AZUL_MENU);
        menu.setBorder(new EmptyBorder(25, 18, 20, 18));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("PREVITECH");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        menu.add(logo);

        JLabel sub = new JLabel("Manutencao de TI");
        sub.setForeground(new Color(190, 200, 255));
        sub.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(sub);

        menu.add(Box.createVerticalStrut(30));

        adicionarItemMenu(menu, "  Inicio", "inicio", telaAtual);
        adicionarItemMenu(menu, "  Login", "login", telaAtual);
        adicionarItemMenu(menu, "  Cadastro", "cadastro", telaAtual);
        adicionarItemMenu(menu, "  Dashboard", "dashboard", telaAtual);
        adicionarItemMenu(menu, "  Chamado", "chamado", telaAtual);
        adicionarItemMenu(menu, "  Acompanhamento", "acompanhamento", telaAtual);
        adicionarItemMenu(menu, "  Manutencao", "manutencao", telaAtual);
        adicionarItemMenu(menu, "  Equipamento", "equipamento", telaAtual);
        adicionarItemMenu(menu, "  Administrador", "administrador", telaAtual);
        adicionarItemMenu(menu, "  Estoque", "estoque", telaAtual);
        adicionarItemMenu(menu, "  Gerenciamento", "gerenciamento", telaAtual);
        adicionarItemMenu(menu, "  Sobre", "sobre", telaAtual);
        adicionarItemMenu(menu, "  Sair", "login", telaAtual);

        menu.add(Box.createVerticalGlue());

        JLabel config = new JLabel("Sistema PREVITECH v1.0");
        config.setForeground(new Color(190, 200, 255));
        config.setFont(new Font("Arial", Font.PLAIN, 12));
        config.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(config);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setPreferredSize(new Dimension(975, 70));
        topo.setBackground(Color.WHITE);
        topo.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel breadcrumb = new JLabel("  Painel / " + telaAtual.toUpperCase());
        breadcrumb.setFont(new Font("Arial", Font.BOLD, 14));
        breadcrumb.setForeground(CINZA_TEXTO);
        topo.add(breadcrumb, BorderLayout.WEST);

        JLabel notificacao = new JLabel(
                "Notificacoes: 3    |    Usuario: " + usuarioLogado + "    |    Perfil: " + perfilLogado + "   ");
        notificacao.setFont(new Font("Arial", Font.PLAIN, 14));
        notificacao.setForeground(CINZA_TEXTO);
        topo.add(notificacao, BorderLayout.EAST);

        JPanel direita = new JPanel(new BorderLayout());
        direita.add(topo, BorderLayout.NORTH);
        direita.add(conteudo, BorderLayout.CENTER);

        base.add(menu, BorderLayout.WEST);
        base.add(direita, BorderLayout.CENTER);
        return base;
    }

    private void adicionarItemMenu(JPanel menu, String texto, String tela, String telaAtual) {
        JButton item = new JButton(texto);
        item.setMaximumSize(new Dimension(190, 36));
        item.setHorizontalAlignment(SwingConstants.LEFT);
        item.setFocusPainted(false);
        item.setBorderPainted(false);
        item.setForeground(Color.WHITE);
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.setFont(new Font("Arial", Font.BOLD, 12));
        item.setBackground(tela.equals(telaAtual) ? new Color(52, 68, 155) : AZUL_MENU);
        if (tela.equals(telaAtual)) {
            item.setText("  " + texto.trim());
        }

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!tela.equals(telaAtual))
                    item.setBackground(new Color(40, 52, 125));
            }

            public void mouseExited(MouseEvent e) {
                if (!tela.equals(telaAtual))
                    item.setBackground(AZUL_MENU);
            }
        });

        item.addActionListener(e -> cardLayout.show(mainPanel, tela));
        menu.add(item);
        menu.add(Box.createVerticalStrut(5));
    }

    private JPanel paginaCentralizada() {
        JPanel conteudo = new JPanel(null);
        conteudo.setBackground(FUNDO);
        conteudo.setPreferredSize(new Dimension(900, 610));
        return conteudo;
    }

    private JPanel envolverConteudo(String telaAtual, JPanel conteudo) {
        JPanel externo = new JPanel(new GridBagLayout());
        externo.setBackground(FUNDO);
        externo.add(conteudo);
        return baseComMenu(telaAtual, externo);
    }

    private JPanel telaDashboard() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Dashboard Usuario", 30);
        title.setBounds(0, 10, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Resumo dos chamados e equipamentos cadastrados");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 48, 450, 25);
        conteudo.add(sub);

        JButton novoChamado = botao("+ Abrir Novo Chamado", AZUL);
        novoChamado.setBounds(690, 25, 210, 40);
        novoChamado.addActionListener(e -> cardLayout.show(mainPanel, "chamado"));
        conteudo.add(novoChamado);

        int cardW = 270;
        int espaco = 45;
        int yCards = 100;

        conteudo.add(cardInfo("Chamados Abertos", "Solicitacoes aguardando atendimento tecnico.", 0, yCards, cardW,
                "12", AZUL));
        conteudo.add(cardInfo("Em Andamento", "Chamados atualmente em atendimento pela equipe.", cardW + espaco, yCards,
                cardW, "5", LARANJA));
        conteudo.add(cardInfo("Finalizados", "Atendimentos concluidos com sucesso.", (cardW + espaco) * 2, yCards,
                cardW, "20", VERDE));

        RoundPanel tabela = new RoundPanel(20, Color.WHITE);
        tabela.setLayout(null);
        tabela.setBounds(0, 305, 900, 285);
        tabela.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel ultimos = titulo("Ultimos Chamados", 23);
        ultimos.setBounds(25, 20, 300, 30);
        tabela.add(ultimos);

        String[] chamados = { "Impressora Lazer HP", "Servidor Rack 1U", "Impressora Lazer Epson" };
        String[] status = { "EM ANDAMENTO", "ABERTO", "FINALIZADO" };
        Color[] cores = { LARANJA, AZUL, VERDE };

        for (int i = 0; i < chamados.length; i++) {
            JLabel item = new JLabel(chamados[i]);
            item.setFont(new Font("Arial", Font.PLAIN, 16));
            item.setBounds(25, 70 + i * 60, 350, 30);
            tabela.add(item);

            JLabel st = new JLabel(status[i], SwingConstants.CENTER);
            st.setOpaque(true);
            st.setForeground(Color.WHITE);
            st.setBackground(cores[i]);
            st.setFont(new Font("Arial", Font.BOLD, 11));
            st.setBounds(470, 72 + i * 60, 150, 28);
            tabela.add(st);

            JButton btn = botao("VER DETALHES", AZUL_ESCURO);
            btn.setBounds(660, 70 + i * 60, 190, 32);
            tabela.add(btn);
        }

        conteudo.add(tabela);
        return envolverConteudo("dashboard", conteudo);
    }

    private JPanel cardInfo(String tituloCard, String texto, int x, int y, int largura, String numero, Color cor) {
        RoundPanel card = new RoundPanel(20, Color.WHITE);
        card.setLayout(null);
        card.setBounds(x, y, largura, 170);

        JPanel faixa = new JPanel();
        faixa.setBackground(cor);
        faixa.setBounds(0, 0, 8, 170);
        card.add(faixa);

        JLabel t = new JLabel(tituloCard);
        t.setFont(new Font("Arial", Font.BOLD, 15));
        t.setBounds(25, 18, largura - 40, 25);
        card.add(t);

        JLabel n = new JLabel(numero);
        n.setFont(new Font("Arial", Font.BOLD, 42));
        n.setForeground(cor);
        n.setBounds(25, 50, largura - 60, 50);
        card.add(n);

        JTextArea area = new JTextArea(texto);
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setForeground(CINZA_TEXTO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setBounds(25, 105, largura - 50, 40);
        card.add(area);

        return card;
    }

    private JPanel telaChamado() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Abrir Chamado", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Informe os dados do problema para solicitar atendimento tecnico");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 58, 520, 25);
        conteudo.add(sub);

        RoundPanel form = new RoundPanel(20, Color.WHITE);
        form.setLayout(null);
        form.setBounds(0, 105, 900, 465);

        JLabel solicitanteLabel = new JLabel("Nome do solicitante");
        solicitanteLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        solicitanteLabel.setBounds(25, 25, 300, 25);
        form.add(solicitanteLabel);

        JTextField solicitante = campoTexto("Nome do solicitante");
        solicitante.setBounds(25, 55, 850, 40);
        form.add(solicitante);

        JLabel equipamentoLabel = new JLabel("Equipamento");
        equipamentoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        equipamentoLabel.setBounds(25, 112, 300, 25);
        form.add(equipamentoLabel);

        String[] equipamentos = { "Impressora Lazer HP", "Servidor Rack 1U", "Impressora Lazer Epson", "Mouse",
                "Teclado", "Monitor", "Notebook" };
        JComboBox<String> equipamento = new JComboBox<>(equipamentos);
        equipamento.setBounds(25, 142, 850, 40);
        equipamento.setFont(new Font("Arial", Font.PLAIN, 14));
        form.add(equipamento);

        JLabel descricaoLabel = new JLabel("Descricao do problema");
        descricaoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descricaoLabel.setBounds(25, 200, 300, 25);
        form.add(descricaoLabel);

        JTextArea descricao = new JTextArea();
        descricao.setFont(new Font("Arial", Font.PLAIN, 14));
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setBorder(new CompoundBorder(new LineBorder(CINZA_BORDA), new EmptyBorder(8, 10, 8, 10)));
        JScrollPane scrollDescricao = new JScrollPane(descricao);
        scrollDescricao.setBounds(25, 230, 850, 95);
        form.add(scrollDescricao);

        JLabel prioridadeLabel = new JLabel("Prioridade");
        prioridadeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        prioridadeLabel.setBounds(25, 342, 300, 25);
        form.add(prioridadeLabel);

        String[] prioridades = { "Baixa", "Media", "Alta" };
        JComboBox<String> combo = new JComboBox<>(prioridades);
        combo.setBounds(25, 372, 850, 40);
        combo.setFont(new Font("Arial", Font.BOLD, 14));
        form.add(combo);

        JButton enviar = botao("Enviar Chamado", AZUL);
        enviar.setBounds(25, 425, 850, 42);
        enviar.addActionListener(e -> {
            if (solicitante.getText().trim().isEmpty() || descricao.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o nome do solicitante e a descricao do problema.",
                        "Atencao", JOptionPane.WARNING_MESSAGE);
            } else {
                String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM"));
                String id = String.valueOf(proximoIdChamado++);

                modeloAcompanhamento.addRow(new Object[] {
                        equipamento.getSelectedItem(),
                        id,
                        dataAtual,
                        "ABERTO"
                });

                modeloGerenciamento.addRow(new Object[] {
                        solicitante.getText(),
                        equipamento.getSelectedItem(),
                        "ABERTO",
                        "EDITAR/FINALIZAR"
                });

                String mensagem = "Chamado enviado com sucesso!\n\n"
                        + "ID: " + id + "\n"
                        + "Solicitante: " + solicitante.getText() + "\n"
                        + "Equipamento: " + equipamento.getSelectedItem() + "\n"
                        + "Prioridade: " + combo.getSelectedItem() + "\n"
                        + "Data: " + dataAtual + "\n\n"
                        + "O chamado foi adicionado nas telas Acompanhamento e Gerenciamento.";

                JOptionPane.showMessageDialog(this, mensagem);

                solicitante.setText("");
                descricao.setText("");
                combo.setSelectedIndex(0);
                equipamento.setSelectedIndex(0);

                cardLayout.show(mainPanel, "acompanhamento");
            }
        });
        form.add(enviar);

        conteudo.add(form);
        return envolverConteudo("chamado", conteudo);
    }

    private JPanel telaAcompanhamento() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Acompanhamento", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JTextField busca = campoTexto("Buscar equipamento...");
        busca.setBounds(0, 80, 900, 40);
        conteudo.add(busca);

        JPanel painel = painelComBorda();
        painel.setLayout(new BorderLayout());
        painel.setBounds(0, 145, 900, 390);

        JTable tabela = tabelaPadrao(modeloAcompanhamento);
        ativarBusca(busca, tabela, modeloAcompanhamento);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        conteudo.add(painel);
        return envolverConteudo("acompanhamento", conteudo);
    }

    private JPanel telaManutencao() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Manutencao", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Alertas de manutencao preventiva dos equipamentos");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 58, 520, 25);
        conteudo.add(sub);

        conteudo.add(cardManutencao("Impressora Lazer HP", "Tempo para proxima revisao: 3 dias",
                "Manutencao preventiva proxima do prazo. Recomenda-se realizar a revisao nos proximos dias.", VERMELHO,
                "!", 0, 110));
        conteudo.add(cardManutencao("Servidor Rack 1U", "Tempo para proxima revisao: 7 dias",
                "Equipamento operando normalmente. Proxima manutencao programada dentro do prazo recomendado.", AZUL,
                "*", 0, 300));

        return envolverConteudo("manutencao", conteudo);
    }

    private JPanel cardManutencao(String nome, String tempo, String desc, Color cor, String icone, int x, int y) {
        RoundPanel card = new RoundPanel(20, Color.WHITE);
        card.setLayout(null);
        card.setBounds(x, y, 900, 155);

        JPanel faixa = new JPanel();
        faixa.setBackground(cor);
        faixa.setBounds(0, 0, 15, 155);
        card.add(faixa);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nomeLabel.setBounds(45, 30, 300, 25);
        card.add(nomeLabel);

        JLabel tempoLabel = new JLabel(tempo);
        tempoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        tempoLabel.setForeground(CINZA_TEXTO);
        tempoLabel.setBounds(45, 55, 430, 25);
        card.add(tempoLabel);

        JTextArea descricao = new JTextArea(desc);
        descricao.setFont(new Font("Arial", Font.PLAIN, 13));
        descricao.setForeground(CINZA_TEXTO);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setEditable(false);
        descricao.setBackground(Color.WHITE);
        descricao.setBounds(130, 90, 560, 45);
        card.add(descricao);

        JLabel icon = new JLabel(icone, SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.BOLD, 55));
        icon.setForeground(cor);
        icon.setBounds(760, 45, 80, 80);
        card.add(icon);

        return card;
    }

    private JPanel telaEquipamento() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Equipamento", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel subtitulo = new JLabel("Cadastro e controle dos equipamentos de TI");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitulo.setForeground(CINZA_TEXTO);
        subtitulo.setBounds(0, 58, 500, 25);
        conteudo.add(subtitulo);

        RoundPanel form = new RoundPanel(20, Color.WHITE);
        form.setLayout(null);
        form.setBounds(0, 100, 900, 185);

        JLabel nomeLabel = new JLabel("Nome do equipamento");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nomeLabel.setBounds(25, 20, 250, 25);
        form.add(nomeLabel);

        JTextField nome = campoTexto("Nome do equipamento");
        nome.setBounds(25, 50, 260, 38);
        form.add(nome);

        JLabel setorLabel = new JLabel("Setor");
        setorLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        setorLabel.setBounds(315, 20, 200, 25);
        form.add(setorLabel);

        JTextField setor = campoTexto("Setor");
        setor.setBounds(315, 50, 220, 38);
        form.add(setor);

        JLabel dataLabel = new JLabel("Proxima manutencao");
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        dataLabel.setBounds(565, 20, 220, 25);
        form.add(dataLabel);

        JTextField data = campoTexto("dd/mm/aaaa");
        data.setBounds(565, 50, 200, 38);
        form.add(data);

        JButton cadastrar = botao("Cadastrar Equipamento", AZUL);
        cadastrar.setBounds(25, 115, 250, 40);
        form.add(cadastrar);

        JButton limpar = botao("Limpar", VERMELHO);
        limpar.setBounds(295, 115, 150, 40);
        form.add(limpar);

        conteudo.add(form);

        JPanel painelTabela = painelComBorda();
        painelTabela.setLayout(new BorderLayout());
        painelTabela.setBounds(0, 320, 900, 250);

        String[] colunas = { "Equipamento", "ID", "Setor", "Proxima manutencao", "Status" };
        Object[][] dados = {
                { "Impressora Lazer HP", "075", "Financeiro", "25/04", "ATENCAO" },
                { "Servidor Rack 1U", "076", "TI", "30/04", "NORMAL" },
                { "Mouse", "077", "Recepcao", "10/05", "NORMAL" }
        };

        JTable tabela = tabelaPadrao(dados, colunas);
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);
        conteudo.add(painelTabela);

        cadastrar.addActionListener(e -> {
            if (nome.getText().trim().isEmpty() || setor.getText().trim().isEmpty()
                    || data.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nome, setor e proxima manutencao.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                modelo.addRow(new Object[] { nome.getText(), "NOVO", setor.getText(), data.getText(), "NORMAL" });
                JOptionPane.showMessageDialog(this, "Equipamento cadastrado com sucesso!");
                nome.setText("");
                setor.setText("");
                data.setText("");
            }
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            setor.setText("");
            data.setText("");
        });

        return envolverConteudo("equipamento", conteudo);
    }

    private JPanel telaAdministrador() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Administrador", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Visao geral do sistema para controle administrativo");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 58, 500, 25);
        conteudo.add(sub);

        int cardW = 270;
        int gap = 45;

        String textoChamados = "128\nSolicitacoes registradas no sistema";
        String textoUsuarios = "45\nContas cadastradas na plataforma";
        String textoEstoque = "320\nPecas disponiveis para manutencao";

        conteudo.add(cardAdmin("Total Chamados", textoChamados, AZUL, 0, 120, cardW));
        conteudo.add(cardAdmin("Usuarios", textoUsuarios, CIANO, cardW + gap, 120, cardW));
        conteudo.add(cardAdmin("Estoque", textoEstoque, VERMELHO, (cardW + gap) * 2, 120, cardW));

        JButton relatorio = botao("Gerar Relatorio", AZUL_ESCURO);
        relatorio.setBounds(0, 320, 220, 42);
        relatorio.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "RELATORIO PREVITECH\n\n"
                        + "Total de chamados: 128\n"
                        + "Chamados abertos: 12\n"
                        + "Em andamento: 5\n"
                        + "Finalizados: 20\n"
                        + "Pecas em estoque: 320"));
        conteudo.add(relatorio);

        JButton gerenciar = botao("Gerenciar Chamados", AZUL);
        gerenciar.setBounds(245, 320, 220, 42);
        gerenciar.addActionListener(e -> cardLayout.show(mainPanel, "gerenciamento"));
        conteudo.add(gerenciar);

        JPanel painel = painelComBorda();
        painel.setLayout(new BorderLayout());
        painel.setBounds(0, 390, 900, 180);

        String[] colunas = { "Indicador", "Quantidade", "Situacao" };
        Object[][] dados = {
                { "Chamados Abertos", "12", "ABERTO" },
                { "Chamados em Atendimento", "5", "EM ANDAMENTO" },
                { "Chamados Finalizados", "20", "FINALIZADO" }
        };

        JTable tabela = tabelaPadrao(dados, colunas);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        conteudo.add(painel);

        return envolverConteudo("administrador", conteudo);
    }

    private JPanel cardAdmin(String titulo, String texto, Color cor, int x, int y, int largura) {
        RoundPanel card = new RoundPanel(20, cor);
        card.setLayout(null);
        card.setBounds(x, y, largura, 170);

        String[] partes = texto.split("\\n", 2);
        String numero = partes.length > 0 ? partes[0] : texto;
        String descricao = partes.length > 1 ? partes[1] : "";

        JLabel t = new JLabel(titulo, SwingConstants.CENTER);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Arial", Font.BOLD, 15));
        t.setBounds(15, 18, largura - 30, 25);
        card.add(t);

        JLabel n = new JLabel(numero, SwingConstants.CENTER);
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 34));
        n.setBounds(15, 52, largura - 30, 45);
        card.add(n);

        JTextArea area = new JTextArea(descricao);
        area.setForeground(Color.WHITE);
        area.setBackground(cor);
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBounds(25, 108, largura - 50, 45);
        card.add(area);

        return card;
    }

    private JPanel telaEstoque() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Estoque", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JTextField busca = campoTexto("Buscar item no estoque...");
        busca.setBounds(0, 80, 900, 40);
        conteudo.add(busca);

        JPanel painel = painelComBorda();
        painel.setLayout(new BorderLayout());
        painel.setBounds(0, 145, 900, 330);

        String[] colunas = { "Item", "Quantidade", "Adicionar", "Editar" };
        Object[][] dados = {
                { "Impressora Lazer HP", "350", "+", "Editar" },
                { "Servidor Rack 1U", "350", "+", "Editar" },
                { "Impressora Lazer Epson", "350", "+", "Editar" },
                { "Mouse", "350", "+", "Editar" },
                { "Teclado", "350", "+", "Editar" }
        };

        DefaultTableModel modeloEstoque = new DefaultTableModel(dados, colunas);
        JTable tabela = tabelaPadrao(modeloEstoque);
        ativarBusca(busca, tabela, modeloEstoque);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton adicionar = botao("Adicionar Item", AZUL);
        adicionar.setBounds(0, 505, 190, 40);
        adicionar.addActionListener(e -> {
            String item = JOptionPane.showInputDialog(this, "Nome do item:");

            if (item == null || item.trim().isEmpty()) {
                return;
            }

            String quantidade = JOptionPane.showInputDialog(this, "Quantidade:");

            if (quantidade == null || quantidade.trim().isEmpty()) {
                return;
            }

            try {
                Integer.parseInt(quantidade.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser um numero.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            modeloEstoque.addRow(new Object[] { item, quantidade, "+", "Editar" });
            JOptionPane.showMessageDialog(this, "Item adicionado ao estoque!");
        });
        conteudo.add(adicionar);

        JButton editar = botao("Editar Quantidade", LARANJA);
        editar.setBounds(210, 505, 210, 40);
        editar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item para editar.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int linhaModelo = tabela.convertRowIndexToModel(linha);
            String quantidadeAtual = modeloEstoque.getValueAt(linhaModelo, 1).toString();

            String novaQuantidade = JOptionPane.showInputDialog(this, "Nova quantidade:", quantidadeAtual);

            if (novaQuantidade != null && !novaQuantidade.trim().isEmpty()) {
                try {
                    Integer.parseInt(novaQuantidade.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantidade deve ser um numero.", "Atencao",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                modeloEstoque.setValueAt(novaQuantidade, linhaModelo, 1);
                JOptionPane.showMessageDialog(this, "Quantidade atualizada!");
            }
        });
        conteudo.add(editar);

        JButton excluir = botao("Excluir Item", VERMELHO);
        excluir.setBounds(440, 505, 180, 40);
        excluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item para excluir.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este item?",
                    "Confirmar exclusao", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                int linhaModelo = tabela.convertRowIndexToModel(linha);
                modeloEstoque.removeRow(linhaModelo);
                JOptionPane.showMessageDialog(this, "Item excluido do estoque!");
            }
        });
        conteudo.add(excluir);

        JButton limparFinalizados = botao("Limpar Finalizados", AZUL_ESCURO);
        limparFinalizados.setBounds(0, 585, 230, 35);
        limparFinalizados.addActionListener(e -> {
            int removidos = 0;

            for (int i = modeloGerenciamento.getRowCount() - 1; i >= 0; i--) {
                String status = modeloGerenciamento.getValueAt(i, 2).toString();

                if (status.equals("FINALIZADO")) {
                    modeloGerenciamento.removeRow(i);
                    removidos++;
                }
            }

            JOptionPane.showMessageDialog(this, removidos + " chamados finalizados removidos.");
        });
        conteudo.add(limparFinalizados);

        conteudo.add(painel);
        return envolverConteudo("estoque", conteudo);
    }

    private JPanel telaGerenciamento() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Gerenciamento", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JTextField busca = campoTexto("Buscar chamado...");
        busca.setBounds(0, 80, 900, 40);
        conteudo.add(busca);

        JPanel painel = painelComBorda();
        painel.setLayout(new BorderLayout());
        painel.setBounds(0, 145, 900, 365);

        JTable tabela = tabelaPadrao(modeloGerenciamento);
        ativarBusca(busca, tabela, modeloGerenciamento);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton detalhes = botao("Ver Detalhes do Chamado", AZUL);
        detalhes.setBounds(0, 535, 250, 40);
        detalhes.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Detalhes do chamado\n\n"
                        + "Usuario: Gustavo Braga\n"
                        + "Equipamento: Impressora Lazer HP\n"
                        + "Problema: Falha na impressao\n"
                        + "Status: Aberto\n"
                        + "Prioridade: Alta"));
        conteudo.add(detalhes);

        JButton finalizar = botao("Finalizar Selecionado", VERDE);
        finalizar.setBounds(270, 535, 230, 40);
        finalizar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um chamado na tabela.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int linhaModelo = tabela.convertRowIndexToModel(linha);
            modeloGerenciamento.setValueAt("FINALIZADO", linhaModelo, 2);
            JOptionPane.showMessageDialog(this, "Chamado finalizado com sucesso!");
        });
        conteudo.add(finalizar);

        JButton editarStatus = botao("Editar Status", LARANJA);
        editarStatus.setBounds(520, 535, 170, 40);
        editarStatus.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um chamado para editar.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opcoes = { "ABERTO", "EM ANDAMENTO", "FINALIZADO" };
            String novoStatus = (String) JOptionPane.showInputDialog(
                    this,
                    "Escolha o novo status:",
                    "Editar status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);

            if (novoStatus != null) {
                int linhaModelo = tabela.convertRowIndexToModel(linha);
                modeloGerenciamento.setValueAt(novoStatus, linhaModelo, 2);
                JOptionPane.showMessageDialog(this, "Status atualizado para: " + novoStatus);
            }
        });
        conteudo.add(editarStatus);

        JButton excluir = botao("Excluir Selecionado", VERMELHO);
        excluir.setBounds(710, 535, 190, 40);
        excluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um chamado para excluir.", "Atencao",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este chamado?",
                    "Confirmar exclusao", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                int linhaModelo = tabela.convertRowIndexToModel(linha);
                modeloGerenciamento.removeRow(linhaModelo);
                JOptionPane.showMessageDialog(this, "Chamado excluido com sucesso!");
            }
        });
        conteudo.add(excluir);

        JButton limparFinalizados = botao("Limpar Finalizados", AZUL_ESCURO);
        limparFinalizados.setBounds(0, 585, 230, 35);
        limparFinalizados.addActionListener(e -> {
            int removidos = 0;

            for (int i = modeloGerenciamento.getRowCount() - 1; i >= 0; i--) {
                String status = modeloGerenciamento.getValueAt(i, 2).toString();

                if (status.equals("FINALIZADO")) {
                    modeloGerenciamento.removeRow(i);
                    removidos++;
                }
            }

            JOptionPane.showMessageDialog(this, removidos + " chamados finalizados removidos.");
        });
        conteudo.add(limparFinalizados);

        conteudo.add(painel);
        return envolverConteudo("gerenciamento", conteudo);
    }

    private JPanel telaPerfil() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Perfil do Usuario", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Dados do usuario logado no sistema");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 58, 520, 25);
        conteudo.add(sub);

        RoundPanel card = new RoundPanel(20, Color.WHITE);
        card.setLayout(null);
        card.setBounds(0, 115, 900, 330);

        JLabel avatar = new JLabel("GB", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(AZUL_ESCURO);
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Arial", Font.BOLD, 28));
        avatar.setBounds(35, 35, 100, 100);
        card.add(avatar);

        JLabel nome = new JLabel("Nome: " + usuarioLogado);
        nome.setFont(new Font("Arial", Font.BOLD, 18));
        nome.setBounds(170, 40, 600, 30);
        card.add(nome);

        JLabel email = new JLabel("Email: gustavobraga5676@gmail.com");
        email.setFont(new Font("Arial", Font.PLAIN, 16));
        email.setForeground(CINZA_TEXTO);
        email.setBounds(170, 80, 600, 30);
        card.add(email);

        JLabel perfil = new JLabel("Perfil: " + perfilLogado);
        perfil.setFont(new Font("Arial", Font.PLAIN, 16));
        perfil.setForeground(CINZA_TEXTO);
        perfil.setBounds(170, 120, 600, 30);
        card.add(perfil);

        JLabel setor = new JLabel("Setor: Tecnologia da Informacao");
        setor.setFont(new Font("Arial", Font.PLAIN, 16));
        setor.setForeground(CINZA_TEXTO);
        setor.setBounds(170, 160, 600, 30);
        card.add(setor);

        JLabel status = new JLabel("Status: Ativo");
        status.setFont(new Font("Arial", Font.BOLD, 16));
        status.setForeground(VERDE);
        status.setBounds(170, 200, 600, 30);
        card.add(status);

        JButton voltar = botao("Voltar para Dashboard", AZUL);
        voltar.setBounds(170, 255, 250, 40);
        voltar.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        card.add(voltar);

        conteudo.add(card);
        return envolverConteudo("perfil", conteudo);
    }

    private JPanel telaSobre() {
        JPanel conteudo = paginaCentralizada();

        JLabel title = titulo("Sobre o Sistema", 30);
        title.setBounds(0, 20, 350, 40);
        conteudo.add(title);

        JLabel sub = new JLabel("Informacoes principais para apresentacao do projeto");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setForeground(CINZA_TEXTO);
        sub.setBounds(0, 58, 520, 25);
        conteudo.add(sub);

        RoundPanel card = new RoundPanel(20, Color.WHITE);
        card.setLayout(null);
        card.setBounds(0, 110, 900, 430);

        JLabel nome = new JLabel("PREVITECH - Sistema de Manutencao Preventiva de TI");
        nome.setFont(new Font("Arial", Font.BOLD, 22));
        nome.setForeground(AZUL_ESCURO);
        nome.setBounds(35, 30, 800, 35);
        card.add(nome);

        JTextArea texto = new JTextArea(
                "Objetivo do projeto:\n"
                        + "O PREVITECH foi desenvolvido para auxiliar empresas no controle de chamados tecnicos, "
                        + "manutencao preventiva de equipamentos e gerenciamento de estoque de pecas de TI.\n\n"
                        + "Tecnologias utilizadas:\n"
                        + "- Linguagem Java\n"
                        + "- Biblioteca Swing para interface grafica\n"
                        + "- JTable para exibicao de dados\n"
                        + "- CardLayout para navegacao entre telas\n\n"
                        + "Funcionalidades principais:\n"
                        + "- Login de usuario comum e administrador\n"
                        + "- Cadastro de usuarios\n"
                        + "- Dashboard com resumo dos chamados\n"
                        + "- Abertura e acompanhamento de chamados\n"
                        + "- Cadastro de equipamentos\n"
                        + "- Controle de estoque\n"
                        + "- Gerenciamento administrativo\n"
                        + "- Relatorio simples e validacoes de campos\n\n"
                        + "Conclusao:\n"
                        + "O sistema simula uma aplicacao corporativa com aparencia de sistema web, "
                        + "mas foi desenvolvido como aplicacao desktop em Java Swing.");
        texto.setFont(new Font("Arial", Font.PLAIN, 15));
        texto.setForeground(CINZA_TEXTO);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setEditable(false);
        texto.setBackground(Color.WHITE);
        texto.setBounds(35, 85, 820, 290);
        card.add(texto);

        JLabel autor = new JLabel("Desenvolvedor: Gustavo Braga");
        autor.setFont(new Font("Arial", Font.BOLD, 15));
        autor.setForeground(AZUL);
        autor.setBounds(35, 380, 400, 30);
        card.add(autor);

        conteudo.add(card);
        return envolverConteudo("sobre", conteudo);
    }

    private void ativarBusca(JTextField campoBusca, JTable tabela, DefaultTableModel modelo) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = campoBusca.getText().trim();

                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    private JTable tabelaPadrao(DefaultTableModel modelo) {
        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(48);
        tabela.setFont(new Font("Arial", Font.PLAIN, 15));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowVerticalLines(false);

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 8, 0, 8));

                String texto = value == null ? "" : value.toString().toUpperCase();

                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);

                    if (texto.equals("ABERTO")) {
                        c.setBackground(new Color(225, 238, 255));
                        c.setForeground(new Color(20, 85, 180));
                    } else if (texto.equals("EM ANDAMENTO")) {
                        c.setBackground(new Color(255, 241, 220));
                        c.setForeground(new Color(190, 105, 0));
                    } else if (texto.equals("FINALIZADO") || texto.equals("NORMAL")) {
                        c.setBackground(new Color(225, 247, 236));
                        c.setForeground(new Color(20, 130, 75));
                    } else if (texto.equals("ATENCAO") || texto.equals("ALTA")) {
                        c.setBackground(new Color(255, 230, 230));
                        c.setForeground(new Color(180, 45, 45));
                    }
                }

                if (column == 0 || column == 1) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }

                return c;
            }
        };

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }

        return tabela;
    }

    private JTable tabelaPadrao(Object[][] dados, String[] colunas) {
        JTable tabela = new JTable(new DefaultTableModel(dados, colunas));
        tabela.setRowHeight(48);
        tabela.setFont(new Font("Arial", Font.PLAIN, 15));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowVerticalLines(false);

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 8, 0, 8));

                String texto = value == null ? "" : value.toString().toUpperCase();

                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);

                    if (texto.equals("ABERTO")) {
                        c.setBackground(new Color(225, 238, 255));
                        c.setForeground(new Color(20, 85, 180));
                    } else if (texto.equals("EM ANDAMENTO")) {
                        c.setBackground(new Color(255, 241, 220));
                        c.setForeground(new Color(190, 105, 0));
                    } else if (texto.equals("FINALIZADO") || texto.equals("NORMAL")) {
                        c.setBackground(new Color(225, 247, 236));
                        c.setForeground(new Color(20, 130, 75));
                    } else if (texto.equals("ATENCAO") || texto.equals("ALTA")) {
                        c.setBackground(new Color(255, 230, 230));
                        c.setForeground(new Color(180, 45, 45));
                    }
                }

                if (column == 0 || column == 1) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }

                return c;
            }
        };

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }

        return tabela;
    }

    private JPanel painelComBorda() {
        RoundPanel painel = new RoundPanel(20, Color.WHITE);
        painel.setLayout(new BorderLayout());
        painel.setBorder(new LineBorder(new Color(220, 230, 240), 1, true));
        return painel;
    }
}
