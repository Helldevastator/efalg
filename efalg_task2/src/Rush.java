import java.io.*;
import java.util.*;

public class Rush
{
	static ArrayList<Long> situations;
    
    static HashSet<Long> allSit;
    
    static ArrayList<Car> cars;
  public static void main(String[] args) throws Exception
  {
    PrintWriter out=new PrintWriter("rush.out");
    cars = new ArrayList<>();
    allSit = new HashSet<>();
    situations = new ArrayList<>();
    read();
    
    solve();
    
    out.close();
  }
  
  public static void solve() {
	  boolean isSolved =false;
	  while(!isSolved) {
		  for(int i = 0; i < cars.size();i++) {
			  if(cars.get(i).move(true)) {
				  Long l = enc();
				  if(!allSit.contains(l))
					  situations.add(l);
			  } else  if(cars.get(i).move(false)) {
				  Long l = enc();
				  if(!allSit.contains(l))
					  situations.add(l);
			  }
		  }
	  }
  }
  
  public static Long enc() {
	  Long l = new Long(0);
	  for(int i = cars.size()-1;i <= 0;i++) {
		  l = cars.get(i).encode(l);
	  }
	  
	  return l;
  }
  
  public static void dec(Long l) {
	  for(int i = 0; i < cars.size();i++) {
		  l = cars.get(i).decode(l);
	  }
  }
  
  public static void read() throws Exception {
	  BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("rush.in")), 100000)));
	  char[][] field = new char[8][8];
	  for(int i = 0; i < 6;i++) {
		  field[i] = in.readLine().toCharArray();
	  }
	  
	  for(int i = 0; i < 6;i++) {
		  for(int j = 0;j < 6;j++) {
			  if(field[i][j] != '.') 
				  readCar(field,i,j);
				  
		  }
	  }
	  
  }
  
  public static void readCar(char[][] field,int x, int y) {
	  if(field[x][y] == 'A') {
		  cars.add(0,new Car(false,x,y,2));
	  } else {
		  boolean isVert = field[x+1][y] == '.' ? true: false;
		  boolean empty = false;
		  int i = 0;
		  while(!empty) {
			  char c = 0;
			  if(isVert) 
				  c = field[x+i][y];
			  else
				  c = field[x][y+1];
			  
			  if(c != '.') {
				  i++;
			  } else {
				  empty = true;
			  }
			  
		  }
		  
		  cars.add(new Car(isVert,x,y,i));
	  }
  }
  
  public static class Car {
	  boolean isVert;
	  int row;
	  int col;
	  int size;
	  
	  public Car(boolean or, int row, int col, int size) {
		  this.row = row;
		  this.col = col;
		  this.size = size;
		  
	  }
	  
	  public Long decode(Long input) {
		  int val = (int) (input & 111);
		  if(isVert)
			  col = val;
		  else
			  row = val;
		  input = input >>> 3;
		  return input;
	  }
	  
	  public Long encode(Long output)  {
		  int val = isVert ? row: col;
		  
		  output = output << 3;
		  output += val;
		  return output;
	  }
	  
	  public boolean move(boolean inc) {
		  boolean possible = false;
		  if(inc) {
			  
		  } else {
			  
		  }
		  return possible;
	  }
  }
}