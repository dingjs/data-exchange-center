package data.exchange.center.service.meishan;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class TestBase64 {

	public static void main(String[] args) {
		String base = "PGh0bWw+DQo8aGVhZD4NCjxNRVRBIGh0dHAtZXF1aXY9IkNvbnRlbnQtVHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PUdCMjMxMiI+DQo8c3R5bGUgdHlwZT0idGV4dC9jc3MiPi5iMXt3aGl0ZS1zcGFjZS1jb2xsYXBzaW5nOnByZXNlcnZlO30NCi5iMnttYXJnaW46IDEuMGluIDEuMTgxMjVpbiAxLjBpbiAxLjE4MTI1aW47fQ0KLnMxe2NvbG9yOmJsYWNrO30NCi5wMXt0ZXh0LWFsaWduOmNlbnRlcjtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTrLzszlO2ZvbnQtc2l6ZToyMnB0O30NCi5wMnt0ZXh0LWFsaWduOmNlbnRlcjtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTrLzszlO2ZvbnQtc2l6ZToyNnB0O30NCi5wM3t0ZXh0LWluZGVudDowLjU4MjYzODg2aW47dGV4dC1hbGlnbjpjZW50ZXI7aHlwaGVuYXRlOmF1dG87Zm9udC1mYW1pbHk6t8LLzjtmb250LXNpemU6MTRwdDt9DQoucDR7dGV4dC1pbmRlbnQ6MC40MDkwMjc3OWluO3RleHQtYWxpZ246ZW5kO2h5cGhlbmF0ZTphdXRvO2ZvbnQtZmFtaWx5OrfCy85fR0IyMzEyO2ZvbnQtc2l6ZToxNnB0O30NCi5wNXt0ZXh0LWluZGVudDowLjQyMDEzODlpbjt0ZXh0LWFsaWduOmp1c3RpZnk7aHlwaGVuYXRlOmF1dG87Zm9udC1mYW1pbHk6t8LLzl9HQjIzMTI7Zm9udC1zaXplOjE2cHQ7fQ0KLnA2e3RleHQtaW5kZW50OjAuNDQ0NDQ0NDVpbjt0ZXh0LWFsaWduOmp1c3RpZnk7aHlwaGVuYXRlOmF1dG87Zm9udC1mYW1pbHk6t8LLzl9HQjIzMTI7Zm9udC1zaXplOjE2cHQ7fQ0KLnA3e3RleHQtaW5kZW50OjAuMjIyMjIyMjJpbjttYXJnaW4tbGVmdDowLjExMTExMTExaW47dGV4dC1hbGlnbjpqdXN0aWZ5O2h5cGhlbmF0ZTphdXRvO2ZvbnQtZmFtaWx5OrfCy85fR0IyMzEyO2ZvbnQtc2l6ZToxNnB0O30NCi5wOHt0ZXh0LWluZGVudDowLjM5MzA1NTU2aW47dGV4dC1hbGlnbjpqdXN0aWZ5O2h5cGhlbmF0ZTphdXRvO2ZvbnQtZmFtaWx5OrfCy85fR0IyMzEyO2ZvbnQtc2l6ZToxNnB0O30NCi5wOXt0ZXh0LWluZGVudDowLjMzMzMzMzM0aW47bWFyZ2luLWxlZnQ6MC4xMjU2OTQ0NGluO3RleHQtYWxpZ246anVzdGlmeTtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTq3wsvOX0dCMjMxMjtmb250LXNpemU6MTZwdDt9DQoucDEwe3RleHQtaW5kZW50OjAuMzQ0NDQ0NDVpbjttYXJnaW4tbGVmdDowLjExMTExMTExaW47dGV4dC1hbGlnbjpqdXN0aWZ5O2h5cGhlbmF0ZTphdXRvO2ZvbnQtZmFtaWx5OrfCy85fR0IyMzEyO2ZvbnQtc2l6ZToxNnB0O30NCi5wMTF7dGV4dC1pbmRlbnQ6MC40MDkwMjc3OWluO3RleHQtYWxpZ246anVzdGlmeTtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTq3wsvOX0dCMjMxMjtmb250LXNpemU6MTZwdDt9DQoucDEye21hcmdpbi1yaWdodDowLjMzMzMzMzM0aW47dGV4dC1hbGlnbjplbmQ7aHlwaGVuYXRlOmF1dG87Zm9udC1mYW1pbHk6t8LLzl9HQjIzMTI7Zm9udC1zaXplOjE2cHQ7fQ0KLnAxM3ttYXJnaW4tcmlnaHQ6MC4xMTExMTExMWluO3RleHQtYWxpZ246ZW5kO2h5cGhlbmF0ZTphdXRvO2ZvbnQtZmFtaWx5OrfCy85fR0IyMzEyO2ZvbnQtc2l6ZToxNnB0O30NCi5wMTR7bWFyZ2luLXJpZ2h0OjAuMDc2Mzg4ODlpbjt0ZXh0LWFsaWduOmVuZDtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTq3wsvOX0dCMjMxMjtmb250LXNpemU6MTZwdDt9DQoucDE1e3RleHQtYWxpZ246anVzdGlmeTtoeXBoZW5hdGU6YXV0bztmb250LWZhbWlseTq3wsvOX0dCMjMxMjtmb250LXNpemU6MTBwdDt9DQo8L3N0eWxlPg0KPHRpdGxlPsvEILSoIMqhIMLeIL2tIM/YIMjLIMPxILeoINS6PC90aXRsZT4NCjxtZXRhIGNvbnRlbnQ9Is/Ez/6+1SIgbmFtZT0iYXV0aG9yIj4NCjwvaGVhZD4NCjxib2R5IGNsYXNzPSJiMSBiMiI+DQo8cCBjbGFzcz0icDEiPg0KPHNwYW4gY2xhc3M9InMxIj7LxCC0qCDKoSDC3iC9rSDP2CDIyyDD8SC3qCDUujwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwMiI+DQo8c3BhbiBjbGFzcz0iczEiPsPxysK1973iyuk8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDMiPjwvcD4NCjxwIGNsYXNzPSJwNCI+DQo8c3BhbiBjbGFzcz0iczEiPqOoMjAxNqOptKgwNjI2w/Gz9TMwNrrFPC9zcGFuPg0KPC9wPg0KPHAgY2xhc3M9InA1Ij48L3A+DQo8cCBjbGFzcz0icDYiPg0KPHNwYW4gY2xhc3M9InMxIj7Urbjms8LTwqOsxNCjrLq61+WjrMn609oxOTgyxOoxMNTCMTfI1aOs16HLxLSoyqHC3r2tz9iw18LtudjV8sj9sua607TlOdfpMji6xaOsuavD8cntt926xcLrNTEwNjAyMTk4MjEwMTc1Nzc2oaM8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDYiPg0KPHNwYW4gY2xhc3M9InMxIj7Or83QtPrA7cjL1tzG9Mn6o6zLxLSot7bU88LJyqbKws7xy/nCycqmoaM8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDYiPg0KPHNwYW4gY2xhc3M9InMxIj6xu7jmwt69rc/Y1tqzx9Kz0dLXqbOno6zXocv5tdijrMLeva3P2LDXwu252NXyyP2y5rrTtOU51+mhozwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwNiI+DQo8c3BhbiBjbGFzcz0iczEiPs2218rIy8H1uKO5+qOsuMOzp7Ons6Shozwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwNiI+DQo8c3BhbiBjbGFzcz0iczEiPrG7uObB9bijufqjrMTQo6y6utflo6zJ+tPaMTk2OcTqM9TCMTbI1aOs16HLxLSoyqG98MzDz9jI/dDH1fLW8cHWMtfpo6y5q8Pxye233brFwus1MTAxMjExOTY5MDMxNjEyMTShozwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwNyI+DQo8c3BhbiBjbGFzcz0iczEiPrG+1LrT2jIwMTbE6jTUwjEyyNXBorC4ytzA7cHL1K245rPC08LL37G7uObC3r2tz9jW2rPH0rPR0teps6ehosH1uKO5+tTLyuS6z82svsC319K7sLiho9LAt6jTybT6wO3J88XQ1LHB9dOmzsTKytPDvPLS17PM0PK5q7+qv6rNpcnzwO2hozwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwOCI+DQo8c3BhbiBjbGFzcz0iczEiPrG+sLi+rbG+1LrW97PWtfe94qOsy6u3vbWxysLIy9fU1Li077PJyOfPwtCt0umjujwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwOSI+DQo8c3BhbiBjbGFzcz0iczEiPrG7uObC3r2tz9jW2rPH0rPR0teps6ehorG7uObB9bijufq/27P90tHP8tStuOazwtPC1qe4trXE1MvK5L/uMTAwMNSquvPU2danuLbNz8e31K245rPC08K1xNTLyuS/7jI5MjPUqqOsuMO/7rao09oyMDE2xOo21MIzMMjVx7DWp7i2MjkyM9SqtcSw2bfW1q7I/cquo6y2qNPaMjAxNsTqMTDUwjMwyNXHsNanuLYyOTIz1Kq1xLDZt9bWrsj9yq6jrLao09oyMDE2xOoxMtTCMzDI1cew1qe4tjI5MjPUqrXEsNm31tauy8TKrqGjPC9zcGFuPg0KPC9wPg0KPHAgY2xhc3M9InAxMCI+DQo8c3BhbiBjbGFzcz0iczEiPrG+sLjV98rVsLi8/srcwO230TI11Kossbu45sLeva3P2Nbas8fSs9HS16mzp6GiwfW4o7n619TUuMirsr+4urWjoaM8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDciPg0KPHNwYW4gY2xhc3M9InMxIj7Lq7e9tbHKwsjL0rvWws2s0uKjrLG+tfe94tCt0um+rcurt721scrCyMvU2rX3veLQrdLpyc/HqcP7oaLe4NOhxvDJ+tCnoaM8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDExIj4NCjxzcGFuIGNsYXNzPSJzMSI+yc/K9tCt0umjrLK7zqW3tLeowsm55raoo6yxvtS60+jS1Mi3yM+hozwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwMTIiPjwvcD4NCjxwIGNsYXNzPSJwMTMiPg0KPHNwYW4gY2xhc3M9InMxIj60+sDtyfPF0NSxICDB9SDTpiDOxDwvc3Bhbj4NCjwvcD4NCjxwIGNsYXNzPSJwMTQiPg0KPHNwYW4gY2xhc3M9InMxIj4gICC2/k/Su8H5xOrLxNTCtv7KrtK7yNU8L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDE0Ij4NCjxzcGFuIGNsYXNzPSJzMSI+yukgILzHICDUsSAgxtEgICAgur08L3NwYW4+DQo8L3A+DQo8cCBjbGFzcz0icDE1Ij48L3A+DQo8L2JvZHk+DQo8L2h0bWw+DQo=";
		byte[] by = Base64.decodeBase64(base.getBytes());
		File file = new File("D://test.htm");  
        BufferedOutputStream stream = null;  
        FileOutputStream fstream = null;  
        byte[] data=new byte[(int)file.length()];  
        try {  
            fstream = new FileOutputStream(file);  
            stream = new BufferedOutputStream(fstream);  
            stream.write(by);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (stream != null) {  
                    stream.close();  
                }  
                if (null != fstream) {  
                    fstream.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
        }  
	}
}
