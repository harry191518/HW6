/*

RLai(Yi-Hao Lai)
HW6.java
Clustering work
input a file (data) and output two file (cluster1.csv cluster2.csv)
clustering data to two group 

*/


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;

public class HW6 {

	public static void main(String[] args) throws IOException {
		int[][] data = new int[436][18];
		int num = 1;
		FileReader fr = new FileReader("data");
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!=null){
			int j = 0;
			char[] l = line.toCharArray();
			for(int i=0;i<l.length;i++){
				if(l[i] == 'y')
					data[num][j++] = 1000;
				if(l[i] == 'n')
					data[num][j++] = 0;
				if(l[i] == '?')
					data[num][j++] = 500;
			}
			data[num][j++] = 1;
			data[num++][j] = 436;
		}
		/*for(int i=0; i<435; i++)
		{
			for(int j=0; j<18; j++)
				System.out.printf(data[i][j]+",");
			System.out.printf("\n");
		}*/
		br.close();
		
		int loop = 1;
		while(loop == 1)
		{	
			int dis, mindis = 16000000, p1 = 0, p2 = 0, c = 0;
			for(int i=1; i<436; i++)
			{
				if(data[i][17] != 436) continue;
				for(int k=i+1; k<436; k++)
				{
					if(data[k][17] != 436) continue;
					dis = 0;
					for(int j=0; j<16; j++)
						dis += (data[i][j]-data[k][j])*(data[i][j]-data[k][j]);
					if(dis < mindis)
					{
						mindis = dis;
						p1 = i;
						p2 = k;
					}	
				}
			}
			data[p1][17] = p2;
			for(int j=0; j<16; j++)
				data[p2][j] = (data[p2][j]*data[p2][16]+data[p1][j]*data[p1][16]) / (data[p1][16]+data[p2][16]);
			data[p2][16] += data[p1][16];
			
			for(int i=1; i<436; i++)
			{
				if(data[i][17] == 436)
					c++;
			}
			//System.out.println(c);
			if(c == 2)
				loop = 0;
		}		
		for(int i=1; i<436; i++)
			while(data[i][17] != 436 && data[data[i][17]][17] != 436)
				data[i][17] = data[data[i][17]][17];
		
		int a1 = 0, a2 = 0;
		for(int i=1; i<436; i++)
			if(data[i][17] == 436)
			{
				if(a1 == 0) a1 = i;
				else a2 = i;
				data[i][17] = i;
			}
		
		FileWriter fw1 = new FileWriter("cluster1.csv");
		FileWriter fw2 = new FileWriter("cluster2.csv");
		
		for(int i=1; i<436; i++)
			if(data[i][17] == a1)
				fw1.write(i+"\n");
			else if(data[i][17] == a2)
				fw2.write(i+"\n");
		
		fw1.close();
		fw2.close();
	}
}
