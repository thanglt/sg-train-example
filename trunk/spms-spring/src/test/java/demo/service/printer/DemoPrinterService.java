package demo.service.printer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DemoPrinterService {
	
	private static final Logger log=LoggerFactory.getLogger(DemoPrinterService.class);
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		ServerSocket.setSocketFactory(new SocketImplFactory(){
//
//			@Override
//			public SocketImpl createSocketImpl() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		});
		
//		ServerSocket service=new ServerSocket(5555);
		
//		
//		Socket socket=service.accept();
//		service.get
		ServerSocketChannel serviceChan=ServerSocketChannel.open();
	    serviceChan.socket().bind(new InetSocketAddress(5555));
	    
		SocketChannel chan=serviceChan.accept();
		
        String info=null;
        while(!"end".equals(info)){
        	
        	ByteBuffer buf=ByteBuffer.allocate(1024);    		
            chan.read(buf);
            buf.flip();
            info=new String(buf.array());
            
        	String out="length:"+info.length();
        	ByteBuffer dst=ByteBuffer.wrap(out.getBytes());
        	chan.write(dst);        	
        }
        
       
		
	}

}
