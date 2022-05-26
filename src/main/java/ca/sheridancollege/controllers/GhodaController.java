package ca.sheridancollege.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GhodaController {
	
	private int matrix[][];
    
    private int vertical[] = {-2, -1, 1, 2, 2 ,1 ,-1, -2};
    private int horizontal[] = {1, 2, 2, 1, -1, -2, -2, -1};
    private int numHops = 1;
    private int hopCounter = 1;
    private boolean universalCounter = true;
    int coorX ;
     int coorY ;
     double total = 0;
    @GetMapping("/")
	public String home() {
		return "ghoda.html";
	}
    @GetMapping("/ghoda")
    public void KTour(@RequestParam String name,@RequestParam int x, @RequestParam int y, @RequestParam int trial )
    {
    	try {
     		File f1 = new File("target/path.txt");
     		
 		    if (!f1.exists()) {
 		        f1.createNewFile();
 		    }
 		    FileWriter fw1 = new FileWriter(f1,false);
 		    fw1.write("");
 		    fw1.close();
 		    
     		}
     		catch(IOException e) {
     			
     		}
         try {
     		File f2 = new File("target/moves.txt");
     		
 		    if (!f2.exists()) {
 		        f2.createNewFile();
 		    }
 		    FileWriter fw2 = new FileWriter(f2,false);
 		    
 		    fw2.write("");
 		    fw2.close();
 		    
     		}
     		catch(IOException e) {
     			
     		}
        for(int i=0;i<trial;i++) {
        	matrix = new int[8][8];
             hopCounter = 1;
             universalCounter = true;
             coorX = x;
             coorY=y;
             
             
        	fillMatrix();
        	matrix[x][y] = 1;
            while(universalCounter==true) {
            	hopFrom(coorX,coorY);
            }
            print(name);
            total+=hopCounter;
        }
        average(name,trial);
        
    }
    public void average(String name, int trial ) {
    	try {
    		File f3 = new File("target/average.txt");
    		
		    if (!f3.exists()) {
		        f3.createNewFile();
		    }
		    FileWriter fw3 = new FileWriter(f3,false);
		    String mean = name+" ran "+(total/trial)+" number of average moves\n";
		    fw3.write(mean);
		    fw3.close();
		    
    		}
    		catch(IOException e) {
    			
    		}
    }
    
    public boolean wasItJumpable(int row, int col)
    {
        return (row >= 0 && row < 8 && col >= 0 && col < 8 && matrix[row][col]==0);
    }
    
    public void fillMatrix()
    {
        for (int r = 0; r < matrix.length; r++)
        {
            for (int c = 0; c < matrix[r].length; c++)
            {
                matrix[r][c] = 0;
                
            }
        }
        
         
    }
    
    public void print(String name)
    {
    	StringBuilder ghodaBuilder = new StringBuilder();
        
		
		for(int i=0; i<8;i++) {
			for(int j=0; j<8;j++) {
				ghodaBuilder.append(matrix[i][j]+"\t");
				}
			ghodaBuilder.append("\n");
		}
		ghodaBuilder.append("\n");
		try {
    		File f1 = new File("target/path.txt");
    		
		    if (!f1.exists()) {
		        f1.createNewFile();
		    }
		    FileWriter fw1 = new FileWriter(f1,true);
		    fw1.write(ghodaBuilder.toString());
		    fw1.close();
		    
    		}
    		catch(IOException e) {
    			
    		}
		try {
    		File f2 = new File("target/moves.txt");
    		
		    if (!f2.exists()) {
		        f2.createNewFile();
		    }
		    FileWriter fw2 = new FileWriter(f2,true);
		    
		    fw2.write(name+" ran "+hopCounter+" number of moves\n");
		    fw2.close();
		    
    		}
    		catch(IOException e) {
    			
    		}
    }
    
    private void hopFrom(int row, int col)
    {
    	ArrayList<Integer> validR = new ArrayList<>();
    	ArrayList<Integer> validC = new ArrayList<>();
       
        
        for(int i=0; i<8;i++){
        
           
            int newRow = row + vertical[i];
            int newCol = col + horizontal[i];
            if ((wasItJumpable(newRow,newCol))==true)
            {
            	validR.add(newRow);
            	validC.add(newCol);

            }
            
                }
        if(validR.isEmpty()) {
        	universalCounter = false;
        }
        else {
        	int random = (int)(Math.random()*validR.size());   
        	hopCounter++;
        	coorX = validR.get(random);
        	coorY = validC.get(random);
        	matrix[coorX][coorY]=hopCounter;
        	 
        }
    }   
    
    
    
    
	
}
