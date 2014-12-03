package br.com.nova.core;



/**
 * @class ChronometerUtil
 * Classe criada para cronometrar os processos da aplicação
 * @author Jairo
 *
 */
public final class ChronometerUtil{
	
	
    private long begin, end;

     
    
    public void start(){
        begin = System.currentTimeMillis();
    }
    public void start(String tag, String message){
        begin = System.currentTimeMillis();
        
    }
    public void start(String message){
        begin = System.currentTimeMillis();
        
    }
    public void stop(){
        end = System.currentTimeMillis();
    }
    public String stop(String tag, String message){
        end = System.currentTimeMillis();
       return (message + " : " + getMilliseconds() + " milisegundos.");
    }
    public String stop(String message){
        end = System.currentTimeMillis();
        return ( message + " : " + getMilliseconds() + " milisegundos.");
    }
   
    public long getTime() {
        return end-begin;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public double getSeconds() {
        return (end - begin) / 1000.0;
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
 
    
}