import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class NetworkInterface {

	private String IP;//not sure we need this
	private Player player;
	private String connectionAddress="http://localhost";
	private String PORT="4000";
	private String connectUrl=connectionAddress+":"+PORT;
	private URL url=new URL(connectUrl);
	
	public NetworkInterface()throws MalformedURLException
	{
		setIP();
	}
	
	void initialize()
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			
			String requestString="{\"type\":\"initialize\",\"PlayerID\":\""+player.getPlayerID()+ "\",\"PlayerIP\":\""+IP+"\",\"PopulationSize\":\""+player.getPopulationSize()+"\",\"MaxFitness\":\""+player.getMaxFitness()+"\",\"MostFitGenotype\":\""+player.getMostFitGenotype()+"\",\"Temperature\":\""+player.getTemperature()+"\",\"pH\":\""+player.getPh()+"\",\"Radiation\":\""+player.getRadiation()+"\",\"Port\":\""+player.getPort()+"\"}";
			OutputStream outstream=connection.getOutputStream();
			//System.out.println(requestString);
			byte[] outgoing=requestString.getBytes(StandardCharsets.UTF_8);
			connection.connect();
			outstream.write(outgoing);
			outstream.close();
			
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			//System.out.println(response.toString());
			if(!response.toString().equals("\"Completed\""))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	void lowerOpponentTemperature(String opponentID)
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"lowerTemperature\",\"PlayerID\":\"" +opponentID +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			if(response.toString().equals("Completed"))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	void raiseOpponentTemperature(String opponentID)
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"raiseTemperature\",\"PlayerID\":\"" +opponentID +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			if(response.toString().equals("Completed"))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	void raiseOpponentPh(String opponentID)
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"raisePh\",\"PlayerID\":\"" +opponentID +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			if(response.toString().equals("Completed"))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	void lowerOpponentPh(String opponentID)
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"lowerPh\",\"PlayerID\":\"" +opponentID +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			if(response.toString().equals("Completed"))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	void alterOpponentRadiation(String opponentID)
	{
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"alterRadiation\",\"PlayerID\":\"" +opponentID +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			if(response.toString().equals("Completed"))
			{
				System.err.println("Error on initialization");
				System.err.println(response.toString());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	HashMap<String,String> updateOpponentData()
	{
		String responseData="";
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"UpdateOpponentData\",\"PlayerID\":\"" +player.getPlayerID() +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				//response.append(responseLine.trim());
				responseData+=responseLine.trim();
			}
			//responseData=response.toString();
			responseData=responseData.replaceAll("\\\\", "");
			responseData=responseData.substring(1);
			responseData=responseData.substring(0,responseData.length()-1 );
			System.out.println(responseData);
			
			JSONParser parser=new JSONParser();
			JSONObject object=(JSONObject)parser.parse(responseData);
			JSONArray ids=(JSONArray)object.get("id");
			JSONArray types=(JSONArray)object.get("mostFit");
			HashMap<String,String> opponentData=new HashMap<String,String>();
			for(int i=0; i<ids.size();i++)
			{
				opponentData.put((String)ids.get(i), (String)types.get(i));
			}
			return opponentData;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	HashMap<String,Double> updateOpponentFitness()
	{
		String responseData="";
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			String requestString="{\"type\":\"UpdateOpponentFitness\",\"PlayerID\":\"" +player.getPlayerID() +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=requestString.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				//response.append(responseLine.trim());
				responseData+=responseLine.trim();
			}
			//responseData=response.toString();
			responseData=responseData.replaceAll("\\\\", "");
			responseData=responseData.substring(1);
			responseData=responseData.substring(0,responseData.length()-1 );
			System.out.println(responseData);
			JSONParser parser=new JSONParser();
			JSONObject object=(JSONObject)parser.parse(responseData);
			JSONArray ids=(JSONArray)object.get("id");
			JSONArray fitnesses=(JSONArray)object.get("fitness");
			HashMap<String,Double> opponentData=new HashMap<String,Double>();
			for(int i=0; i<ids.size();i++)
			{
				opponentData.put((String)ids.get(i), Double.parseDouble((String)fitnesses.get(i)));
			}
			return opponentData;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	String migrateOut(HashMap<String,Integer> emmigrants)
	{
		//figure out a way to implement this
		System.out.println(emmigrants.toString());
		JSONObject reqObj=new JSONObject();
		JSONArray migratorsTypes=new JSONArray();
		JSONArray migratorsNumbers=new JSONArray();
		Iterator<Entry<String, Integer>> type=emmigrants.entrySet().iterator();
		Map.Entry<String, Integer> nextType;
		while (type.hasNext())
		{
			//JSONObject newArrayItem=new JSONObject();
			nextType=(Map.Entry<String, Integer>)type.next();
			//newArrayItem.put(nextType.getKey(),nextType.getValue() );
			migratorsTypes.add(nextType.getKey());
			migratorsNumbers.add(nextType.getValue());
		}
		reqObj.put("type", "migrate");
		reqObj.put("migrantTypes", migratorsTypes);
		reqObj.put("migrantNumbers",migratorsNumbers );
		String reqValue=reqObj.toJSONString();
		String responseData="";
		try {
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			//String requestString="{\"type\":\"UpdateOpponentFitness\",\"PlayerID\":\"" +player.getPlayerID() +"\"}";
			OutputStream outstream=connection.getOutputStream();
			byte[] outgoing=reqValue.getBytes("utf-8");
			outstream.write(outgoing,0,outgoing.length);
			//response
			BufferedReader inReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuilder response=new StringBuilder();
			String responseLine=null;
			while((responseLine=inReader.readLine())!=null)
			{
				response.append(responseLine.trim());
			}
			responseData=response.toString();
			System.out.println(responseData);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return responseData;
	}
	void setPlayer(Player newPlayer)
	{
		player=newPlayer;
	}
	void setIP()
	{
		String ip;
		try(final DatagramSocket socket=new DatagramSocket())
		{
			socket.connect(InetAddress.getByName("8.8.8.8"),10002);
			ip=socket.getLocalAddress().getHostAddress();
			IP=ip;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
