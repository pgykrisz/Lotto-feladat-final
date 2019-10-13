package lotto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lotto2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Kérem az 52. hét lottószámait");

        //bekérem az adatokat
        int utolsoHet[] = new int[5];
        for (int i = 0; i < 5; i++) {
            utolsoHet[i] = sc.nextInt();
        }

        //sorrendbe állítom a bekért adatokat
        int csere;
        for (int i = 0; i < utolsoHet.length - 1; i++) {
            for (int j = i + 1; j < utolsoHet.length; j++) {
                if (utolsoHet[i] > utolsoHet[j]) {
                    csere = utolsoHet[i];
                    utolsoHet[i] = utolsoHet[j];
                    utolsoHet[j] = csere;
                }
            }
        }

        //kiiratom a bekért adatokat
        for (int i = 0; i < utolsoHet.length; i++) {
            System.out.print(utolsoHet[i] + " ");
        }
        System.out.println();
        System.out.println("************************");

        //3. feladat, bekérem a hetet
        System.out.println("Adj meg egy számot 1 és 51 között");
        int het = sc.nextInt();

        //beolvasom a filet 4. feladathoz
        int nyeroSzamok[][] = new int[52][5];
        String heti;
        String[] szamok;
        try {
            FileReader fr = new FileReader("lottosz.dat");
            BufferedReader br = new BufferedReader(fr);
            for (int i = 0; i < 51; i++) {
                heti = br.readLine();
                szamok = heti.split(" ");
                for (int j = 0; j < szamok.length; j++) {
                    nyeroSzamok[i][j] = Integer.parseInt(szamok[j]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File nem található!");
        } catch (IOException ex) {
            System.out.println("Olvasási hiba!");
        }

        //4. feladat, kiírom a megadott hét számait
        System.out.println("A/az " + het + ". hét lottószámai:");
        for (int i = 0; i < 5; i++) {
            System.out.print(nyeroSzamok[het - 1][i] + " ");
        }
        System.out.println();
        System.out.println("**************************");

        //5. feladat, egyszer sem húzták ki?
        int[] szamokDb = new int[90];
        for (int i = 0; i < nyeroSzamok.length - 1; i++) {
            for (int j = 0; j < nyeroSzamok[i].length - 1; j++) {
                szamokDb[nyeroSzamok[i][j] - 1]++;
            }
        }
        boolean van = false;
        for (int sz : szamokDb) {
            if (sz == 0) {
                van = true;
                break;
            }
        }

        if (van == true) {
            System.out.println("Van");
        } else {
            System.out.println("Nincs");
        }
        System.out.println("*****************************");

        //6. feladat, a kihúzott számok közül mennyi volt a páratlan?
        int db = 0;
        for (int i = 0; i < szamokDb.length; i++) {
            if (i % 2 == 0) {
                db = db + szamokDb[i];
            }

        }
        System.out.println("A kihúzott számok közül " + db + " db volt páratlan");

        //7. feladat
        for (int i = 0; i < 5; i++) {
            nyeroSzamok[51][i] = utolsoHet[i];
        }
        try {

            FileWriter fw = new FileWriter("lotto52.ki");
            PrintWriter pr = new PrintWriter(fw);
            for (int i = 0; i < nyeroSzamok.length; i++) {
                for (int j = 0; j < 4; j++) {
                    pr.write(nyeroSzamok[i][j] + " ");

                }

                pr.write(nyeroSzamok[i][4] + "\r\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Írási hiba");
        }
        
        //8. feladat
        
        //A bekért 52. hetet hozzászámolom
        for( int i = 0; i < utolsoHet.length; i++ )
      {
        szamokDb[utolsoHet[i]-1]++;
      }
        
        //összeszámolom
        System.out.println("\n"+"8-as feladat:"+"\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < szamokDb.length; i++) {
            sb.append(szamokDb[i]+" ");
            if( (i+1) % 15 ==0 )
                sb.append("\n");
        }
        System.out.println(sb.toString().replaceAll(" \n", "\n"));
        
        //9. feladat
        System.out.println("Az alábbi prímszámokat nem húzták ki:");
        int[] primek = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89};
        for (int sz : primek){
            if( szamokDb[sz-1] == 0 )
                System.out.println(sz+" "); 
        }
    }

}
