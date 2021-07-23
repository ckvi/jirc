package org.jirc.util;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * StringFunctions.java contains static functions that will be used to analyse incoming and outgoing traffic
 * to the protocoll's needs.
 * 
 * @version 0.0.1
 * @author jirc development team
 * @since 0.0.0
 */
public class StringFunctions {
	
	/**
	 * Prints a given text to the standard output.
	 * @param Echo - text that should get echoed.
	 */
	public static void printEcho(String Echo){
		
            System.out.println(Echo + " " + Echo);
	}
        
        public static String analyzeUserInput(String input){
            
            Pattern PrivMsg = Pattern.compile("/private .*$");
            Pattern Join = Pattern.compile("/join #.*$");
            Pattern Quit = Pattern.compile("/quit( .*)*$");
            Pattern Notice = Pattern.compile("/notice .*$");
            Pattern Part = Pattern.compile("/part");
            
            
            
            if((PrivMsg.matcher(input)).matches())
                System.out.println("/private wurde eingegeben");
            if((Join.matcher(input)).matches()){
                System.out.println("/join wurde eingegeben");
                return "join";
            }
            if((Quit.matcher(input)).matches()){
                System.out.println("/quit wurde eingegeben");
                return "quit";
            }
            if((Notice.matcher(input)).matches())
                System.out.println("/notice wurde eingegeben");
            
            if((Part.matcher(input)).matches()){
                System.out.println("/part wurde eingegeben");
                return "part";
            }
            
            
     
            
            return "ok";
        }
        
        public static String analyzeServerInput(String input){
            
            Pattern ChannelPrivMsg = Pattern.compile("^:[a-zA-Z\"|\"]+![a-zA-Z]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+.* PRIVMSG #[a-zA-Z0-9]+ :.*+$");
            Pattern userPart = Pattern.compile("^:[a-zA-Z\"|\"]+![a-zA-Z]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+.* PART #[a-zA-Z0-9]+$");
            Pattern userJoin = Pattern.compile("^:[a-zA-Z\"|\"]+![a-zA-Z]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+.* JOIN :#[a-zA-Z0-9]+$");
            Pattern userQuit;
            Pattern userNotice;
            Pattern userMode;
            Pattern userList = Pattern.compile(".*353.*:.*$");
            
            
            if((ChannelPrivMsg.matcher(input)).matches()){
                System.out.println("ChannelPrivMsg");
                return "ChannelPrivMsg";
            }
            if((userList.matcher(input)).matches()){
                System.out.println("userList");
                return "userList";
            }
            if((userPart.matcher(input)).matches()){
                System.out.println("userPart");
                return "userPart";
            }
            if((userJoin.matcher(input)).matches()){
                System.out.println("userJoin");
                return "userJoin";
            }
            
            
            return "ok";
        }
        
        public static String getPrivMsgChannel(String s){
            
            int index = s.indexOf('#');
            String split[]=s.split(" ");
            return split[2];
        }
        
        public static String getJoinChannel(String s){
            
            int index = s.indexOf('#');
            return s.substring(index);
        }
        
        public static String getUserListChannel(String s){
            
            int index_hash = s.indexOf('#');
            int index_blank= s.indexOf(' ',index_hash);
            System.out.println("getUserListChannel: "+s);
            return s.substring(index_hash,index_blank);
        }
        
        public static String[] getChannelUsers(String s){
            
            String split[]=s.split(":",3);
            split=split[2].split(" ");
            return split;
        }
        
        public static String getPartUser(String s){
            
            int index_start=1;
            int index_end=s.indexOf("!");
            return s.substring(index_start,index_end);
        }
        public static String getJoinUser(String s){
            
            int index_start=1;
            int index_end=s.indexOf("!");
            return s.substring(index_start,index_end);
        }
        
        public static String getUserPartChannel(String s){
            
            int index=s.indexOf("#");
            return s.substring(index);
        }
        public static String getUserJoinChannel(String s){
            
            int index=s.indexOf("#");
            return s.substring(index);
        }
        
        public static boolean checkUrl(String s){
            
            Pattern url = Pattern.compile(".*http://.*$");
            
            if((url.matcher(s)).matches()){
                System.out.println("String enthält Url");
                return true;
            }
            else
                return false;
        }
}
