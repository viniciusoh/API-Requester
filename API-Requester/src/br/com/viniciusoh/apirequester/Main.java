package br.com.viniciusoh.apirequester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static JTextField[] headerTextFields;
    public static JTextField[] paramsTextFields;
    public static RequestManager requestManager;
    
    public static JTextArea txtOutput;
    
    public static void main(String[] args) {
        requestManager = new RequestManager(getUrl(), RequestManager.REQUEST_TYPE_GET);
        
	    // write your code here
        JFrame janela = new JFrame("API Requester - " + getUrl());
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
        txtUrlPanel.add(new JLabel(getUrl()));
        p1.add(txtUrlPanel);

        p1.add(new JLabel("Headers"));
        headerTextFields = new JTextField[getHeadersCount()];
        for (int i=0; i<getHeadersCount(); i++) {
            Panel p = new Panel();
            BoxLayout box = new BoxLayout(p,BoxLayout.X_AXIS);
            p.setLayout(box);
            p.add(new JLabel(getHeader(i))); // Adiciona um botão
            headerTextFields[i] = new JTextField("");
            p.add(headerTextFields[i]);
            p1.add(p);
        }

        p1.add(new JLabel("Params"));
        paramsTextFields = new JTextField[getParamsCount()];
        for (int i=0; i<getParamsCount(); i++) {
            Panel p = new Panel();
            BoxLayout box = new BoxLayout(p,BoxLayout.X_AXIS);
            p.setLayout(box);
            p.add(new JLabel(getParam(i))); // Adiciona um botão
            paramsTextFields[i] = new JTextField("");
            p.add(paramsTextFields[i]);
            p1.add(p);
        }

        Panel btnPannel = new Panel();
        BoxLayout box = new BoxLayout(btnPannel, BoxLayout.X_AXIS);
        btnPannel.setLayout(box);
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> onClickEnviar());
        btnPannel.add(btnEnviar);

        p1.add(btnPannel);

        JScrollPane scrollP1 = new JScrollPane(p1);
        ScrollPaneLayout scrollPaneLayoutP1 = new ScrollPaneLayout();
        scrollP1.setLayout(scrollPaneLayoutP1);
        caixa.add(scrollP1);


        txtOutput = new JTextArea("");
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);

//        p2.add(textArea);
        JScrollPane p2 = new JScrollPane(txtOutput);
        ScrollPaneLayout scrollPaneLayoutP2 = new ScrollPaneLayout();
        p2.setLayout(scrollPaneLayoutP2);
        caixa.add(p2);

//        janela.pack();
        janela.setVisible(true); // Exibe a janela
    }

    private static String getUrl() {
        return "https://api.trello.com/1/members/me/boards?key=keynumber&token=tokennumber";
    }

    private static void onClickEnviar(){
        Map<String,String> headers = new HashMap<>();
        if (headerTextFields != null) {
            for (int i = 0; i < headerTextFields.length; i++) {
                headers.put(getHeader(i), headerTextFields[i].getText());
            }
        }

        Map<String,String> params = new HashMap<>();
        if (paramsTextFields != null) {
            for (int i = 0; i < paramsTextFields.length; i++) {
                params.put(getHeader(i), paramsTextFields[i].getText());
            }
        }
        
        requestManager.makeRequest(headers, params, (success, response) -> {
            txtOutput.setText(response);
        });
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
