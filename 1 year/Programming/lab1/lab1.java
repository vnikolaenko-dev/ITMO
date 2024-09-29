// import java.util.Math;
import java.util.Arrays;

public class lab0{
	public static void main(String[] args){
		int[] lst = {4, 5, 7, 10, 11, 15, 16};

		// Список типа long
		long[] c = new long[15];
		for (int i = 0; i <= 14; i++)
			c[i] = 16 - i;

		// Список типа Double
		double[] x = new double[16];
		for (int i = 0; i < 16; i++)
			x[i] = (Math.random() * 18) - 7;

		// Вложенный список типа Double
		double[][] sp = new double[15][16];
		for (int i=0; i < 15; i++){
			for (int j=0; j < 16; j++){

				if (c[i] == 9){
					sp[i][j] = Math.sin(Math.toRadians(x[j]));
				}

				else if (c[i] == 4 || c[i] == 5 || c[i] == 7 || c[i] == 10 || c[i] == 11 || c[i] == 15 || c[i] == 16){
					sp[i][j] = Math.pow(Math.pow((0.25 * Math.asin((x[j] + 2) / 18)), 2), Math.pow(0.5 / (x[j] + 1), 2) + 1 / Math.cos(Math.toRadians(Math.pow(Math.E, x[j]))));
				}

				else{
					sp[i][j] = Math.cos(Math.toRadians(Math.pow(Math.E, Math.sin(Math.asin((x[j] + 2) / 18)))));
				}
			}
		}

		for (int i=0; i < 15; i++){
			for (int j=0; j < 16; j++){
				System.out.printf("%.4f ", sp[i][j]);
			}
			System.out.println();
		}


		// System.out.println(Arrays.toString(spd));
		
	}
}