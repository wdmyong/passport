nginx的sever需作如下配置：

server {
        listen       80;
        server_name test.wdm.com,test.cel.com;

        location / {
            proxy_pass   http://127.0.0.1:8080;
        }
    }

hosts添加如下配置:
127.0.0.1	localhost,test.wdm.com,test.cel.com
