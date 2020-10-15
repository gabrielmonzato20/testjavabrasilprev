# # Test brasilprev

Teste para a brasilprev utilizando java com Spring boot e oracle db 
## Instalação

O sistema foi esta modularizado com [docker](https://docs.docker.com/get-docker/)
## oracle
Usaremos docker para criar um container do banco para isso devemos seguir os seguintes passos:
1- logue no dockerhub
```bash
docker login
```
2- baixe a imagen do oracle db
```bash
 docker pull store/oracle/database-enterprise:12.2.0.1
```
3 - rode a imagen peguando a porta 1521 do container e apontando para 1521 do host
 ```bash
 docker run -d -p 1521:1521 --name oracletest store/oracle/database-enterprise:12.2.0.1
```
4- agora temos um banco agora vamos connectar no container para configurar permissoes schemas e authenticação do banco 
 ```bash
 docker exec -it oracletest bash -c "source /home/oracle/.bashrc; sqlplus /nolog"
```
5 - connectado no container vamos usar um script para criar um usuario:
 ```bash
 connect sys as sysdba;
 ```
 5.1 senha- Oradoc_db1
 ```bash
  alter session set "_ORACLE_SCRIPT"=true;
  create user dummy identified by dummy;
  GRANT ALL PRIVILEGES TO dummy;
  ```
## Spring Boot
1-na pasta root desse projeto execute o seguinte comando para geração de uma imagen docker
```bash
docker build . -t backend
```
2- posteriomente crie um container a partir da imagen linkando com o container do banco oracle
```bash
docker run -p 8086:8086 --name backend-devtest2 --link oracletest:store/oracle/database-enterprise -d backend6
```
