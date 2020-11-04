import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class InboundInterface extends Thread {
	Player playerInterface;
	HttpServer server;
	int PORT;
	public void run()
	{
		try {
			
			server=HttpServer.create(new InetSocketAddress(PORT),3);
			server.createContext("/inbound",new EnvironHandler());
			server.setExecutor(null);
			System.out.println("Starting listening on port: "+PORT);
			server.start();
			System.out.println(server.getAddress());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	class EnvironHandler implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange)throws IOException{
			//System.out.println(exchange.getRequestHeaders().getFirst("Content-Length"));
			//System.out.println(exchange.toString());
			
			if(exchange.getRequestMethod().equalsIgnoreCase("POST"))
			{
				try {
					Headers reqHeaders=exchange.getRequestHeaders();
					Set<Map.Entry<String,List<String>>> entries=reqHeaders.entrySet();
					int length=Integer.parseInt(reqHeaders.getFirst("Content-Length"));
					InputStream inStream=exchange.getRequestBody();
					byte[] data=new byte[length];
					length=inStream.read(data);
					Headers resHeaders=exchange.getResponseHeaders();
					String response="complete";
					byte[] resData=response.getBytes();
					int resLength=resData.length;
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, resLength);
					OutputStream outStream=exchange.getResponseBody();
					outStream.write(resData);
					outStream.flush();
					
					//outStream.close();
					//exchange.close();
					String requestData=new String(data);
					System.out.println(requestData);
					String switchCase[]=requestData.split(" ");
					switch(switchCase[0])
					{
					case("lowerTemperature"):
						playerInterface.bufferTemperatureDecrease();
						break;
					case("raiseTemperature"):
						playerInterface.bufferTemperatureIncrease();
						break;
					case("raisePh"):
						playerInterface.bufferPhIncrease();
						break;
					case("lowerPh"):
						playerInterface.bufferPhDecrease();
						break;
					case("alterRadiation"):
						playerInterface.bufferAlterRadiation();
						break;
					case("GameOver"):
						playerInterface.endGame(true,switchCase[1]);
						break;
					case("Reproduce"):
						playerInterface.triggerReproduction();
						
						break;
					
					case("Begin"):
						playerInterface.beginGame();
						break;
					default:
						handleUpdate(requestData);
						break;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	void setPlayer(Player newPlayer)
	{
		playerInterface=newPlayer;
		PORT=playerInterface.getPort();
	}
	
	void handleUpdate(String data)
	{
		//install json parser and produce code
		try {
		JSONParser parser=new JSONParser();
		JSONObject object=(JSONObject) parser.parse(data);
		JSONArray types= (JSONArray)object.get("types");
		JSONArray number=(JSONArray)object.get("number");
		ArrayList<String>genotypes=new ArrayList<String>();
		ArrayList<Integer>frequencies=new ArrayList<Integer>();
		for(int i=0; i<types.size();i++)
		{
			genotypes.add((String)types.get(i));
			frequencies.add(((Long)number.get(i)).intValue());
		}
		playerInterface.migrate(genotypes, frequencies);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("unexpected request:");
		//System.out.println(data);
		
	}
	void endConnection()
	{
		server.stop(0);
	}
}
