package com.decathlon.cndp_interface_dev.configure;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SSHConnection {

    private final static String S_PATH_FILE_PRIVATE_KEY = "/Users/hdwang/.ssh/id_rsa";
    private final static String S_PATH_FILE_KNOWN_HOSTS = "gateway-bastion-azr-cne2.dktapp.cloud";
    private final static String S_PASS_PHRASE = "";
    private final static int LOCAl_PORT = 1920;
    private final static int REMOTE_PORT = 1921;
    private final static int SSH_REMOTE_PORT = 1022;
    private final static String SSH_USER = "wallixadm@local@DMTFN1TAL01:SSH:z01thuan";
    private final static String SSH_PASSWORD = "122720Ss?";
    private final static String SSH_REMOTE_SERVER = "192.168.0.2";

    //通过SSH 隧道连接后 访问数据资源的服务器地址一般是 localhost或者主机内网IP，不用公网IP
    private final static String MYSQL_REMOTE_SERVER = "123.mysql.com";

    private Session sesion; //represents each ssh session

    public void closeSSH ()
    {
        sesion.disconnect();
    }

    public SSHConnection () throws Throwable
    {

        JSch jsch = null;

        jsch = new JSch();
        jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);

        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);

        sesion.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sesion.setConfig(config);

        sesion.connect(); //ssh connection established!

        //by security policy, you must connect through a fowarded port
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);

    }
}