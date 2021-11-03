package br.com.viniciusoh.apirequester;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        JFrame janela = new JFrame("Título da janela");
        janela.setBounds(300, 200, 1000, (getHeadersCount() + getParamsCount()) * 75); // Seta posição e tamanho

        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container caixa = janela.getContentPane(); // Define o tamanho
        GridLayout gridLayout = new GridLayout(1,2);

        caixa.setLayout(gridLayout); // Seta layout do container

        Panel p1 = new Panel();
        BoxLayout boxLayout = new BoxLayout(p1, BoxLayout.Y_AXIS);
        p1.setLayout(boxLayout);

        Panel txtUrlPanel = new Panel();
        txtUrlPanel.setLayout(new BoxLayout(txtUrlPanel, BoxLayout.X_AXIS));
        txtUrlPanel.add(new JLabel("Url:"));
        txtUrlPanel.add(new JLabel("https://www.trello.com.br/api/v2/etc"));
        p1.add(txtUrlPanel);

        p1.add(new JLabel("Headers"));
        JTextField[] headers = new JTextField[getHeadersCount()];
        for (int i=0; i<getHeadersCount(); i++) {
            Panel p = new Panel();
            BoxLayout box = new BoxLayout(p,BoxLayout.X_AXIS);
            p.setLayout(box);
            p.add(new JLabel(getHeader(i))); // Adiciona um botão
            headers[i] = new JTextField("");
            p.add(headers[i]);
            p1.add(p);
        }

        p1.add(new JLabel("Params"));
        JTextField[] params = new JTextField[getParamsCount()];
        for (int i=0; i<getParamsCount(); i++) {
            Panel p = new Panel();
            BoxLayout box = new BoxLayout(p,BoxLayout.X_AXIS);
            p.setLayout(box);
            p.add(new JLabel(getParam(i))); // Adiciona um botão
            params[i] = new JTextField("");
            p.add(params[i]);
            p1.add(p);
        }

        Panel btnPannel = new Panel();
        BoxLayout box = new BoxLayout(btnPannel, BoxLayout.X_AXIS);
        btnPannel.setLayout(box);
        btnPannel.add(new JButton("Enviar"));
        p1.add(btnPannel);

        JScrollPane scrollP1 = new JScrollPane(p1);
        ScrollPaneLayout scrollPaneLayoutP1 = new ScrollPaneLayout();
        scrollP1.setLayout(scrollPaneLayoutP1);
        caixa.add(scrollP1);


        JTextArea textArea = new JTextArea("");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

//        p2.add(textArea);
        JScrollPane p2 = new JScrollPane(textArea);
        ScrollPaneLayout scrollPaneLayoutP2 = new ScrollPaneLayout();
        p2.setLayout(scrollPaneLayoutP2);
        caixa.add(p2);

//        janela.pack();
        janela.setVisible(true); // Exibe a janela
    }


    private static String getHeader(int i) {
        return "Header " + i;
    }

    private static int getHeadersCount() {
        return 2;
    }

    private static String getParam(int i) {
        return "Param " + i;
    }

    private static int getParamsCount() {
        return 8;
    }
}
