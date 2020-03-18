package encoded;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TranscodingFrame extends JFrame{
    private JLabel pathLabel, fCodeLabel, nCodeLabel;
    private JTextField pathText;
    private JComboBox fCodeComboBox, nCodeComboBox;
    private JButton confirmButton;
    
    
    public TranscodingFrame() {
        this.setTitle("编码转码");
        this.setDecoration();
        this.setSize(400, 120);
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(buildTopPanel(),BorderLayout.NORTH);
        this.add(buildBottomPanel());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel();
        pathLabel = new JLabel("路径:");
        pathText = new JTextField(20);
        panel.add(pathLabel);
        panel.add(pathText);
        panel.add(buildButton());
        return panel;
    }
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();
        fCodeLabel = new JLabel("转换前");
        nCodeLabel = new JLabel("转换后");
        panel.add(fCodeLabel);
        panel.add(buildfCodeComboBox());
        panel.add(nCodeLabel);
        panel.add(buildnCodeComboBox());
        
        return panel;
    }
    
    //确认按钮
    private JButton buildButton() {
        if(confirmButton == null) {
            confirmButton = new JButton("转码");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    String pathName = pathText.getText();
                    if(pathName.equals("")) {
                        JOptionPane.showMessageDialog(null,"路径不能为空！");
                        return;
                    }
                    String fCode = fCodeComboBox.getSelectedItem().toString();
                    String nCode = nCodeComboBox.getSelectedItem().toString();

                    File file = new File(pathName);
                    Vector<File> files = new Vector<File>();
                    Transcoding.getAllFile(file, files);
                    for (int i = 0; i < files.size(); i++) {
                        Transcoding.codedConversion(fCode, nCode, files.get(i));
                    }
                    JOptionPane.showMessageDialog(null, "转码完成！");
                    
                }
            });
        }
        return confirmButton;
    }
    //
    private JComboBox buildfCodeComboBox() {
        if(fCodeComboBox == null) {
            String[] code = {"GBK","UTF-8"}; 
            fCodeComboBox = new JComboBox(code);
        }
        return fCodeComboBox;
    }
    //
    private JComboBox buildnCodeComboBox() {
        if(nCodeComboBox == null) {
            String[] code = {"UTF-8","GBK"}; 
            nCodeComboBox = new JComboBox(code);
        }
        return nCodeComboBox;
    }
    
    /**
     * 皮肤包
     */
    private void setDecoration() {
        try {
            UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new TranscodingFrame().setVisible(true);
    }
}
