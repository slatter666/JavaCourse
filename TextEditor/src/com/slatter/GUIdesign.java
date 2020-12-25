package com.slatter;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/5/4
 * @since 1.0.0
 */
public class GUIdesign extends JFrame {

    private final JFrame frame = new JFrame();
    private final JMenuBar menuBar = new JMenuBar();       //菜单栏
    private final JTextPane textPane = new JTextPane();     //编辑文本
    private final JScrollPane scrollPane = new JScrollPane();     //滚动条
    private final JFileChooser fileChooser = new JFileChooser();    //选择文件
    private final UndoManager um = new UndoManager();
    //定义文件菜单
    private final JMenu fileMenu = new JMenu("文件(F)");
    private final JMenuItem newMenuItem = new JMenuItem("新建", KeyEvent.VK_N);     //新建
    private final JMenuItem openMenuItem = new JMenuItem("打开", KeyEvent.VK_O);     //打开
    private final JMenuItem saveMenuItem = new JMenuItem("保存", KeyEvent.VK_S);     //保存
    private final JMenuItem saveAsMenuItem = new JMenuItem("另存为");   //另存为
    private final JMenuItem exitMenuItem = new JMenuItem("退出");     //退出
    //定义编辑菜单
    private final JMenu editMenu = new JMenu("编辑(E)");
    private final JMenuItem backoutMenuItem = new JMenuItem("撤销", KeyEvent.VK_Z);   //撤销
    private final JMenuItem cutMenuItem = new JMenuItem("剪切", KeyEvent.VK_X);      //剪切
    private final JMenuItem copyMenuItem = new JMenuItem("复制", KeyEvent.VK_C);     //复制
    private final JMenuItem pasteMenuItem = new JMenuItem("粘贴", KeyEvent.VK_V);     //粘贴
    private final JMenuItem deleteMenuItem = new JMenuItem("删除", KeyEvent.VK_DELETE);     //删除
    //定义格式菜单
    private final JMenu formatMenu = new JMenu("格式(O)");
    private final JMenuItem lineWrapMenu = new JMenuItem("自动换行");     //自动换行
    private final JMenu styleMenu = new JMenu("样式");       //样式
    private final JMenuItem typefaceMenu = new JMenuItem("字体");          //字体
    private final JMenuItem colorMenu = new JMenuItem("颜色");       //颜色
    //定义查看菜单
    private final JMenu checkMenu = new JMenu("查看");
    private final JMenu zoomMenu = new JMenu("缩放");    //缩放
    private final JMenuItem enlargeMenu = new JMenuItem("放大", KeyEvent.VK_ADD);    //放大
    private final JMenuItem shrinkMenu = new JMenuItem("缩小", KeyEvent.VK_SUBTRACT);     //缩小
    private final JMenuItem statusbarMenu = new JMenuItem("状态栏");   //状态栏
    //定义帮助菜单
    private final JMenu helpMenu = new JMenu("帮助(H)");
    private final JMenuItem checkHelpMenu = new JMenuItem("查看帮助");    //查看帮助
    private final JMenuItem aboutMenu = new JMenuItem("关于编辑器");    //关于文本编辑器
    private JComboBox comboBoxName;            //字体组合框
    private JComboBox comboBoxType;            //字形组合框
    private JComboBox comboBoxSize;            //字号组合框

    public GUIdesign() {
        initComponents();
    }

    public static void main(String[] args) {
        GUIdesign gui = new GUIdesign();
        gui.setVisible(true);
    }

    private void initComponents() {
        //添加子菜单到文件菜单
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));    //新建
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));   //打开
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));   //保存
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        //添加子菜单到编辑菜单
        backoutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));  //撤销
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));     //剪切
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));    //复制
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));    //粘贴
        editMenu.add(backoutMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(deleteMenuItem);

        //添加子菜单到格式菜单
        formatMenu.add(lineWrapMenu);
        formatMenu.add(styleMenu);
        styleMenu.add(typefaceMenu);
        styleMenu.add(colorMenu);

        //添加子菜单到查看菜单
        enlargeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, ActionEvent.CTRL_MASK));     //放大
        shrinkMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK));   //缩小
        zoomMenu.add(enlargeMenu);
        zoomMenu.add(shrinkMenu);
        checkMenu.add(zoomMenu);
        checkMenu.add(statusbarMenu);

        //添加子菜单到帮助菜单
        helpMenu.add(checkHelpMenu);
        helpMenu.add(aboutMenu);

        //文件菜单下的事件监听
        newMenuItem.addActionListener(new NewAction());
        openMenuItem.addActionListener(new OpenAction());
        saveMenuItem.addActionListener(new SaveAction());
        saveAsMenuItem.addActionListener(new SaveAction());
        exitMenuItem.addActionListener(new ExitAction());

        //编辑菜单下的事件监听
        backoutMenuItem.addActionListener(new BackoutAction());
        cutMenuItem.addActionListener(new CutAction());
        copyMenuItem.addActionListener(new CopyAction());
        pasteMenuItem.addActionListener(new PasteAction());
        deleteMenuItem.addActionListener(new CutAction());

        //格式菜单下的事件监听
        lineWrapMenu.addActionListener(new LineWrapAction());
        typefaceMenu.addActionListener(new TypefaceAction());
        colorMenu.addActionListener(new ColorAction());

        //查看菜单下的事件监听
        enlargeMenu.addActionListener(new EnlargeAction());
        shrinkMenu.addActionListener(new ShrinkAction());
        statusbarMenu.addActionListener(new DefaultAction("状态显示功能不可用"));

        //帮助菜单下的事件监听
        checkHelpMenu.addActionListener(new HelpAction());
        aboutMenu.addActionListener(new AboutAction());

        //添加菜单到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(checkMenu);
        menuBar.add(helpMenu);

        textPane.getDocument().addUndoableEditListener(um);

        //主窗口界面设置
        setSize(500, 500);
        setLocation(500, 150);
        setTitle("简易文本编辑器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        scrollPane.setViewportView(textPane);
        add(scrollPane);
    }

    //实现新建文件命令功能
    class NewAction extends AbstractAction {
        public NewAction() {
            super("新建");
        }

        public void actionPerformed(ActionEvent e) {
            textPane.setDocument(new DefaultStyledDocument());  //清空文档
        }
    }

    //实现打开文件命令功能
    class OpenAction extends AbstractAction { //实现打开文件命令功能
        public OpenAction() {
            super("打开");
        }

        public void actionPerformed(ActionEvent e) {
            int value = fileChooser.showOpenDialog(GUIdesign.this);
            //显示打开文件对话框
            if (value == JFileChooser.APPROVE_OPTION) {     //点击对话框中打开选项
                File file = fileChooser.getSelectedFile();       //得到选择的文件
                try {
                    InputStream input = new FileInputStream(file);        //得到文件输入流
                    textPane.read(input, "d");       //读入文件到文本窗格
                } catch (Exception ex) {
                    ex.printStackTrace();           //输出出错信息
                }
            }
        }
    }

    //实现保存及另存为命令功能
    class SaveAction extends AbstractAction {
        public SaveAction() {
            super("保存");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int value = fileChooser.showSaveDialog(GUIdesign.this);
            if (value == JFileChooser.APPROVE_OPTION) {
                //    private JLabel label = new JLabel();        //标签
                //用于文件操作
                File file = fileChooser.getSelectedFile();
                try {
                    FileOutputStream output = new FileOutputStream(file);
                    output.write(textPane.getText().getBytes());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //实现退出命令功能
    class ExitAction extends AbstractAction {
        public ExitAction() {
            super("退出");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    //实现撤销命令功能
    class BackoutAction extends AbstractAction {
        public BackoutAction() {
            super("撤销");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (um.canUndo()) {
                um.undo();
            }
        }
    }

    //实现剪切命令功能
    class CutAction extends AbstractAction {
        public CutAction() {
            super("剪切");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.cut();
        }
    }

    //实现复制命令功能
    class CopyAction extends AbstractAction {
        public CopyAction() {
            super("复制");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.copy();
        }
    }

    //实现粘贴及删除命令功能
    public class PasteAction extends AbstractAction {
        public PasteAction() {
            super("粘贴");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.paste();
        }
    }

    //实现自动换行命令功能
    class LineWrapAction extends AbstractAction {
        public LineWrapAction() {
            super("自动换行");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            scrollPane.setEnabled(!scrollPane.isEnabled());
        }
    }

    //实现字体修改命令功能
    class TypefaceAction extends AbstractAction {
        public TypefaceAction() {
            super("字体");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setSize(400, 250);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontsName = ge.getAvailableFontFamilyNames();        //获得系统字体;
            comboBoxName = new JComboBox(fontsName);        //字体组合框
            comboBoxName.addActionListener(new OpAciton());                 //组合框注册单击事件监听器
            panel.add(comboBoxName);

            String[] fontType = {"常规", "斜体", "粗体", "粗斜体"};
            comboBoxType = new JComboBox(fontType);          //字形组合框
            comboBoxType.addActionListener(new OpAciton());
            panel.add(comboBoxType);

            int count = 0;
            String[] fontSize = new String[50];
            for (int i = 4; i <= 100; i += 2) {
                fontSize[count] = Integer.toString(i);
                count++;
            }
            comboBoxSize = new JComboBox(fontSize);           //字号组合框
            comboBoxSize.setEditable(true);                 //设置可编辑
            comboBoxSize.addActionListener(new OpAciton());
            panel.add(comboBoxSize);

            frame.setBounds(550, 170, 450, 250);
            frame.setTitle("字体");
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    //实现颜色修改命令功能
    class ColorAction extends AbstractAction {
        public ColorAction() {
            super("颜色");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = new Color(0, 192, 191);
            color = JColorChooser.showDialog(GUIdesign.this, "颜色选择框", color);
            textPane.setForeground(color);
        }
    }

    //实现放大命令功能
    class EnlargeAction extends AbstractAction {
        public EnlargeAction() {
            super("放大");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MutableAttributeSet attr = textPane.getInputAttributes();
            int size = StyleConstants.getFontSize(attr);
            StyleConstants.setFontSize(attr, size + 1);
            StyledDocument doc = textPane.getStyledDocument();
            doc.setCharacterAttributes(0, doc.getLength() + 1, attr, false);
        }
    }

    //实现缩小命令功能
    class ShrinkAction extends AbstractAction {
        public ShrinkAction() {
            super("缩小");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MutableAttributeSet attr = textPane.getInputAttributes();
            int size = StyleConstants.getFontSize(attr);
            StyleConstants.setFontSize(attr, size - 1);
            StyledDocument doc = textPane.getStyledDocument();
            doc.setCharacterAttributes(0, doc.getLength() + 1, attr, false);
        }
    }

    //实现查看帮助命令功能
    class HelpAction extends AbstractAction {
        public HelpAction() {
            super("查看帮助");
        }

        public void actionPerformed(ActionEvent e) {
            try {
                String url = "cmd /c start iexplore https://cn.bing.com/search?q=%E8%8E%B7%E5%8F%96%E6%9C%89%E5%85%B3+windows+10+%E4%B8%AD%E7%9A%84%E8%AE%B0%E4%BA%8B%E6%9C%AC%E7%9A%84%E5%B8%AE%E5%8A%A9&filters=guid:%224466414-zh-hans-dia%22%20lang:%22zh-hans%22&form=T00032&ocid=HelpPane-BingIA";
                Runtime.getRuntime().exec(url);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //实现关于编辑器命令功能
    class AboutAction extends AbstractAction {
        public AboutAction() {
            super("关于编辑器");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GUIdesign.this, "Editor1.0:仅实现部分功能，其余功能正" +
                    "在开发中...\n了解更多请联系开发者:20184413@cqu.edu.cn", "关于编辑器", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //实现字体操作监听功能
    class OpAciton extends AbstractAction {
        public OpAciton() {
            super("字体");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int size = 0;
            try {
                String name = (String) comboBoxName.getSelectedItem();    //获得字体
                String type = (String) comboBoxType.getSelectedItem();      //获得字形
                size = Integer.parseInt((String) comboBoxSize.getSelectedItem());        //获得字号
                if (size < 4 || size > 100) {                   //字号超出指定范围时，抛出异常对象
                    throw new Exception("SizeException");
                }

                Font currentFont = textPane.getFont();
                int style = currentFont.getStyle();
                if (type.equals("粗体")) {
                    style = style ^ 1;
                } else if (type.equals("斜体")) {
                    style = style ^ 2;
                } else if (type.equals("粗斜体")) {
                    style = style ^ 1 ^ 2;
                }
                textPane.setFont(new Font(name, style, size)); //设置文本区字体

            } catch (Exception ex) {
                if (ex.getMessage() == "SizeException") {     //捕获自己抛出的异常对象
                    JOptionPane.showMessageDialog(null, size + " 字号不合适" +
                            "，请重新输入!");
                } else {
                    ex.printStackTrace();
                }
            }
        }
    }

    //正在开发中的命令功能
    class DefaultAction extends AbstractAction {
        String name;

        public DefaultAction(String name) {
            super("正在开发中的功能");
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GUIdesign.this, "该功能正在开发中,敬请期待!", name, JOptionPane.PLAIN_MESSAGE);
        }
    }
}