package com.miracle.livegame;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class my extends JFrame  implements ActionListener  
{  
    JFrame f;  
    JPanel p1,p2;  
    JLabel p;  
    JButton pre= new JButton("前一张");  
    JButton next=new JButton("后一张");  
    ImageIcon []ic={new ImageIcon("img1.gif"),new ImageIcon("img2.gif"),new ImageIcon("img3.gif"),new ImageIcon("img4.gif"),new ImageIcon("img5.gif")};  
    int index=0;  
    public my()  
    {  
        f=new JFrame("图像浏览器");//框架  
        f.setSize(1400,1500);  
        f.setVisible(true);  
        Container c=f.getContentPane();//添加容器  
        c.setLayout(new GridLayout(2,1));  
        p1=new JPanel();//两个面板  
        p2=new JPanel();  
        c.add(p1);  
        c.add(p2);  
        p2.add(pre);  
        p2.add(next);  
        pre.addActionListener(this);  
        next.addActionListener(this);  
        p=new JLabel(ic[index]);  
        p1.add(p);  
    }  
    public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        if(e.getSource()==pre)  
        {  
            if(index==0) index=4;  
            else index--;  
            showPic();  
        }  
        if(e.getSource()==next)  
        {  
            index=(index+1)%5;  
            showPic();  
        }  
    }  
    private void showPic()   
    {  
          p.setIcon(ic[index]);  
          }  

    public static void main(String args[]) throws IOException
    {  
        new my();
        System.out.println(GetMsg());
        System.out.println(getLocalMac());
    }

    public static String GetMsg() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://vrback.youku.com:8080/recommand/recommandonly/videos4kr");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST call CloseableHttpResponse#close() from a finally clause.
        // Please note that if response content is not fully consumed the underlying
        // connection cannot be safely re-used and will be shut down and discarded
        // by the connection manager.
        String content;
        try {
            System.out.println(response1.getStatusLine());

            HttpEntity entity1 = response1.getEntity();
            content = EntityUtils.toString(entity1);
            System.out.println(content);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
        return content;

        // HttpPost httpPost = new HttpPost("http://targethost/login");
        // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // nvps.add(new BasicNameValuePair("username", "vip"));
        // nvps.add(new BasicNameValuePair("password", "secret"));
        // httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        // CloseableHttpResponse response2 = httpclient.execute(httpPost);
        //
        // try {
        // System.out.println(response2.getStatusLine());
        // HttpEntity entity2 = response2.getEntity();
        // // do something useful with the response body
        // // and ensure it is fully consumed
        // EntityUtils.consume(entity2);
        // } finally {
        // response2.close();
        // }
    }

    private static String getLocalMac() throws SocketException, UnknownHostException {
        // TODO Auto-generated method stub
        // 获取网卡，获取地址
        InetAddress ia = InetAddress.getLocalHost();

        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // 字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);

            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }


}  