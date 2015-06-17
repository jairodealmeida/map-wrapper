# Midware interface to map-wrapper. #

Inplements of webservice rest interface to work with midwares in geography information systems context

**IRC** #map-wrapper

**Developer guide of map-wrapper project**

**How to configure?**
First of all, set the **map-wrapper**,the liked database **jdbc**  library in your classpath and configurate the database instance in your project,
in source folder in your project you will create a **context.xml** file
and setting the configuration to database instance of project.

**example to postgresql database**
```
## define a type of database connection
database.type = postgres

## postgresql connection
database.postgres.url = jdbc:postgresql://localhost:5432/test_database
database.postgres.driver = org.postgresql.Driver
database.postgres.user = test
database.postgres.pass = test
```


**Geoserver implementation**

##Database PostgreSQL + PostGIS

Is necessary config any spatial functions
```
create or replace function st_estimated_extent(text,text) 
returns box2d as 'select null::box2d' language sql;

create or replace function st_estimated_extent(text,text,text) 
returns box2d as 'select null::box2d' language sql;
```

**System developer config**
**Configuração de Ambiente de desenvolvimento**

Caso seja no servidor executar
```
ssh -p5050 -i /home/jairodealmeida/Jacto/servidor/sshkeys/id_rsa ext_nv_jalmeida@200.98.168.89
```
**Senha m****o**



**Instalação do tomcat7**
```
sudo apt-get install tomcat7 tomcat7-docs tomcat7-examples tomcat7-admin
```

**Se houver esta menssagem é necessário configurar a variavel de ambiente JAVA\_HOME** no JDK found - please set JAVA\_HOME Editar o arquivo de configurações do tomcat7**```
sudo nano /etc/default/tomcat7
```**Adicionar a linha com o caminho do java instalado**JAVA\_HOME=/usr/lib/jvm/java-7-oracle**


**Configurações para o tomcat**
```
##Arquivo de inicialização
##$CATALINA_HOME/bin/startup.sh
## Arquivo de configuração de acesso ao webmanager (gerenciamento)
sudo nano /etc/tomcat7/tomcat-users.xml
```

**Incluir a anotação para acesso externo**
```
<tomcat-users>
...
<role rolename="manager-gui"/>
<role rolename="admin-gui"/>
<user username="tomcat" password="tomcat" roles="manager-gui,admin-gui"/>
</tomcat-users>
```

**Caso seja necessário reinicializar**
```
sudo /etc/init.d/tomcat7 restart
```

**Caso seja necessário (opcional)
```
sudo nano ~/.bashrc
cd /usr/share/tomcat7
```**

**Configurações de servidor e porta**
```
sudo nano /etc/tomcat7/server.xml
```
**Alterar a porta do servido para não conflitar com a do glassfish**
```
 <Service name="Catalina">
   ...
   <Connector executor="tomcatThreadPool"
               port="8084" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
 
 </Service>
```
**Comando para inicialização do tomcat7**
**Geralmente fica na iniciado no caminho**
```
## http://localhost:8080
sudo /etc/init.d/tomcat7 start
```
**Se houver a necessidade de restart no serviço seria o comando**
```
sudo /etc/init.d/tomcat7 restart
## Ou parar o tomcat
sudo /etc/init.d/tomcat7 stop
```

**Para fazer o deploy do geoserver**
**Descompactar o arquivo versão geoserver war file,**
**pegar o arquivo geoserver.war**
```
sudo wget  "http://ares.boundlessgeo.com/geoserver/2.6.x/geoserver-2.6.x-latest-war.zip"
sudo chmod 777 geoserver-2.6.x-latest-war.zip 
sudo unzip geoserver-2.6.x-latest-war.zip 
sudo cp -i geoserver.war /var/lib/tomcat7/webapps/

 ##Pegar o projeto .war do aplicativo geoserver e coloca-lo na pasta /var/lib/tomcat7/webapps/

##O deploy do projeto geoserver será gerado automaticamente no caminho
/var/lib/tomcat7/webapps/geoserver/
 
 
##para executar o aplicativo geoserver digite em um navegador web
http://seu_host:sua_porta/geoserver
```

**O usuário default é admin e a senha é geoserver**

**Aumentando a capacidade da requisição via GET** (opcional)
**Configuração pode ser feita no arquivo  de configurações do tomcat7,**abra o **terminal e digite:**
```
cd /var/lib/tomcat7/conf/
sudo nano server.xml
```

**Imcluir a linha em negrito e savar o arquivo**
```
< Connector port="8084" maxHttpHeaderSize="100000" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```

**Reiniciar logo a seguir**
```
sudo /etc/init.d/tomcat7 restart
```

**Se houver um erro de CATALINA\_HOME**
```
root@ubuntu12test:~# vi /usr/share/tomcat7/bin/setenv.sh
export CATALINA_BASE=/var/lib/tomcat7
export CATALINA_HOME=/usr/share/tomcat7
Verifying setenv.sh
root@ubuntu12test:~# cat /usr/share/tomcat7/bin/setenv.sh
export CATALINA_BASE=/var/lib/tomcat7
export CATALINA_HOME=/usr/share/tomcat7
```



**Configurando datasource para o map-wrapper**
```
sudo nano /var/lib/tomcat7/conf/context.xml 
```
**Logo após editar o arquivo com as configurações do banco de dados**
```
     <Resource 
               name="jdbc/otmisnetds" auth="Container" 
               type="javax.sql.DataSource" 
               driverClassName="org.postgresql.Driver" 
               url="jdbc:postgresql://localhost:15432/otmisnet" 
               username="postgres" password="queijo" 
               maxActive="20" maxIdle="10" maxWait="-1"/> 
```


**Acessar**
```
http://localhost:8084/geoserver
```
**Login default** Sendo possível configurar uma nova senha no painel de configurações
Usuário : admin
Senha : geoserver

Criação de uma **workspace** : otmisnet\_workspace
Criação de uma **store** : otmisnet\_datasource
Entrar com o nome do JNDI configurado otmisnetds
jndiReferenceName
```
java:comp/env/jdbc/otmisnetds
```

**Opcional - Se houver erro na identificação do datasource (pool de conexões)**
**Erro : org.apache.tomcat.jdbc.pool.PooledConnection.connectUsingDriver(PooledConnection)**
Necesário copiar o drive JDBC para dentro da pasta
/usr/share/tomcat7/lib
Essa dependencia se encontra no seguinte caminho, (maven dependency)
/home/jairodealmeida/.m2/repository/org/postgresql/postgresql/9.3-1100-jdbc41/postgresql-9.3-1100-jdbc41.jar
```
sudo cp /home/jairodealmeida/.m2/repository/org/postgresql/postgresql/9.3-1100-jdbc41/postgresql-9.3-1100-jdbc41.jar /usr/share/tomcat7/lib
sudo chmod 777 /usr/share/tomcat7/lib/postgresql-9.3-1100-jdbc41.jar
```
**Erro de Permissão Negada para acesso a arquivo configuração wfs**
Também se houver um erro de permissão é necessário definir da seguinte maneira
```
sudo chmod 777 /home/jairodealmeida/Jacto/OtmisNet/geoserver_data/wfs.xml
```


**Optional UML diagram software**
http://alexdp.free.fr/violetumleditor/page.php?id=en:installation
```
jairodealmeida@jairodealmeida:/opt/eclipse/plugins$ cp /home/jairodealmeida/Downloads/com.horstmann.violet_0.20.0.jar /opt/eclipse/plugins/
cp: cannot create regular file ‘/opt/eclipse/plugins/com.horstmann.violet_0.20.0.jar’: Permission denied
jairodealmeida@jairodealmeida:/opt/eclipse/plugins$ sudo cp /home/jairodealmeida/Downloads/com.horstmann.violet_0.20.0.jar /opt/eclipse/plugins/
```
Restart eclipse IDE

OR
Baixar e executar
java -jar violetumleditor-2.0.1.jar



**Author** : Jairo de Almeida
**Data** : 02/12/2014