import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// VARIABELEN ______________________________________________________________
		Scanner sc = new Scanner(System.in);
		final int MAX = Integer.MAX_VALUE;
		// GEVALLEN OVERLOPEN
		int n, geval;
		// EIGENSCHAPPEN VAN EEN VELD
		int knopen, lijnen;
		int R_count, K_count;
		int[][] graaf;
		// UITKOMST
		int M_count;
		int source, dest, weight;
		int[] mijnen_uitkomst;
		int mijnen_totaal;
		int[] afstand_uitkomst;
		int afstand_totaal;
		// ITERATIE
		int row, kolom;
		int knoop, lijn;
		int loper;

		// START VAN HET PROGRAMMA _________________________________________________
		n = sc.nextInt();
		for (geval = 0; geval < n; geval++) {
			// String[] kaartparameters = sc.nextLine().split(" ");
			R_count = sc.nextInt();
			K_count = sc.nextInt();

			// NORMAAL GESPROKEN HEBBEN WE EEN VELD VAN MEER DAN 1 RIJ EN/ OF KOLOM
			if (K_count != 1 && R_count != 1) {
				knopen = R_count * K_count + 2;
				lijnen = 2 * K_count * (2 * R_count - 1);
				graaf = new int[lijnen][];
				loper = 0;
				for (row = 0; row < R_count; row++) {
					for (kolom = 0; kolom < K_count; kolom++) {
						// LEES AANTAL MIJNEN IN VAN VOLGENDE ZONE
						M_count = sc.nextInt();
						// BOVENSTE LIJN
						if (row == 0) {
							graaf[loper] = new int[] { K_count + kolom + 1, kolom + 1, M_count };
							loper++;
							// BOVENSTE LIJN - LINKSE KOLOM
							if (kolom == 0) {
								graaf[loper] = new int[] { 0, 1, M_count };
								loper++;
								graaf[loper] = new int[] { 2, 1, M_count };
								loper++;
							}
							// BOVENSTE LIJN - RECHTS KOLOM
							else if (kolom == K_count - 1) {
								graaf[loper] = new int[] { K_count - 1, K_count, M_count };
								loper++;
								graaf[loper] = new int[] { K_count, knopen - 1, 0 };
								loper++;
							}
							// BOVENSTE LIJN - MIDDEN
							else {
								graaf[loper] = new int[] { kolom, kolom + 1, M_count };
								loper++;
								graaf[loper] = new int[] { 2, 1, M_count };
								loper++;
							}
						}
						// ONDERSTE LIJN
						else if (row == R_count - 1) {
							graaf[loper] = new int[] { (row - 1) * K_count + 1 + kolom, knopen - K_count - 1 + kolom,
									M_count };
							loper++;
							// ONDERSTE LIJN - LINKSTE KOLOM
							if (kolom == 0) {
								graaf[loper] = new int[] { 0, knopen - K_count - 1, M_count };
								loper++;
								graaf[loper] = new int[] { knopen - K_count, knopen - K_count - 1, M_count };
								loper++;
							}
							// ONDERSTE LIJN - RECHTS KOLOM
							else if (kolom == K_count - 1) {
								graaf[loper] = new int[] { knopen - 3, knopen - 2, M_count };
								loper++;
								graaf[loper] = new int[] { knopen - 2, knopen - 1, 0 };
								loper++;
							}
							// ONDERSTE LIJN - MIDDEN
							else {
								graaf[loper] = new int[] { row * K_count + kolom, row * K_count + kolom + 1, M_count };
								loper++;
								graaf[loper] = new int[] { row * K_count + kolom + 2, row * K_count + kolom + 1,
										M_count };
								loper++;
							}
						}
						// MIDDENSTE LIJNEN
						else {
							graaf[loper] = new int[] { (row - 1) * K_count + 1 + kolom, row * K_count + 1 + kolom,
									M_count };
							loper++;
							graaf[loper] = new int[] { (row + 1) * K_count + 1 + kolom, row * K_count + 1 + kolom,
									M_count };
							loper++;
							// MIDDENSTE LIJNEN - LINKSE KOLOM
							if (kolom == 0) {
								graaf[loper] = new int[] { 0, row * K_count + 1, M_count };
								loper++;
								graaf[loper] = new int[] { row * K_count + 2, row * K_count + 1, M_count };
								loper++;
							}
							// MIDDENSTE LIJNEN - RECHTS KOLOM
							else if (kolom == K_count - 1) {
								graaf[loper] = new int[] { (row + 1) * K_count - 1, (row + 1) * K_count, M_count };
								loper++;
								graaf[loper] = new int[] { (row + 1) * K_count, knopen - 1, 0 };
								loper++;
							}
							// MIDDENSTE -MIDDEN
							else {
								graaf[loper] = new int[] { row * K_count + kolom, row * K_count + kolom + 1, M_count };
								loper++;
								graaf[loper] = new int[] { row * K_count + kolom + 2, row * K_count + kolom + 1,
										M_count };
								loper++;
							}
						}
					}
				}
				// VERWERKING
				mijnen_uitkomst = new int[knopen];
				afstand_uitkomst = new int[knopen];
				Arrays.fill(mijnen_uitkomst, MAX);
				Arrays.fill(afstand_uitkomst, 1);
				mijnen_uitkomst[0] = 0;
				afstand_uitkomst[0] = 0;

				// PAS MINIMALISATIE TOE VOOR ELKE COMBO KNOOP&LIJN OP MIJEN&AFSTAND
				for (knoop = 1; knoop < knopen; ++knoop) {
					for (lijn = 0; lijn < lijnen; ++lijn) {
						source = graaf[lijn][0];
						dest = graaf[lijn][1];
						weight = graaf[lijn][2];
						// INDIEN HET PAD VEILIGER KAN, MAAK HET VEILIGER
						if (mijnen_uitkomst[source] != MAX
								&& mijnen_uitkomst[source] + weight < mijnen_uitkomst[dest]) {
							mijnen_uitkomst[dest] = mijnen_uitkomst[source] + weight;
							afstand_uitkomst[dest] = afstand_uitkomst[source] + 1;
						}
						// INDIEN HET NIET VEILIGER KAN, MAAR WEL KORTER, MAAK HET KORTER
						else if (mijnen_uitkomst[source] + weight == mijnen_uitkomst[dest]) {
							afstand_uitkomst[dest] = Math.min(afstand_uitkomst[source] + 1, afstand_uitkomst[dest]);
						}
					}
				}
				// OUTPUT
				afstand_totaal = afstand_uitkomst[knopen - 1] - 1;
				mijnen_totaal = mijnen_uitkomst[knopen - 1];
				System.out.println((geval + 1) + " " + afstand_totaal + " " + mijnen_totaal);
			}
			// HET GEVAL WAAR WE SLECHTS 1 KOLOM HEBBEN, BEHANDELEN WE APART
			else if (K_count == 1) {
				// VERWERKING
				M_count = MAX;
				for (row = 0; row < R_count; row++) {
					M_count = Math.min(M_count, sc.nextInt());
				}
				// OUTPUT
				System.out.println((geval + 1) + " 1 " + M_count);
			}

			// HET GEVAL WAAR WE SLECHTS 1 RIJ HEBBEN, BEHANDELEN WE APART
			else if (R_count == 1) {
				// VERWERKING
				M_count = 0;
				for (kolom = 0; kolom < K_count; kolom++) {
					M_count = M_count + sc.nextInt();
				}
				// OUTPUT
				System.out.println((geval + 1) + " " + K_count + " " + M_count);
			}
		}
		sc.close();
	}
}
