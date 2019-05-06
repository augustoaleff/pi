

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSockets {
	private static String OUTPUT = "<html><head><title>Projeto Integrado</title></head><body><p>Hello World!!!</p></body></html>";
	private static String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n"
			+ "Content-Length: ";
	private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

	public static void main(String[] args) throws IOException {

		@SuppressWarnings("resource")
		ServerSocket servidor = new ServerSocket(12344);

		Thread servidorThread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {

					try {

						System.out.println("Servidor ouvindo a porta 12344");

						Socket cliente = servidor.accept();

						Thread requisicaoThread = new Thread(new Runnable() {

							@Override
							public void run() {

								String linha = null;
								
								try {

									
									
									BufferedReader br = new BufferedReader(
											new InputStreamReader(cliente.getInputStream()));
									String line = null;
									while ((line = br.readLine()) != null) {

										System.out.println(line);

										if (line.startsWith("GET")) {
											linha = line;
											if (linha != null) {

												// String path = "C:/public" + linha.replace("GET ", "").replace("
												// HTTP/1.1",
												// "");
												
												//String path = "C:\\Users\\x481822\\Downloads\\Entrega 04-20190424T133452Z-001\\Entrega 04\\sj"
												String path = "C:\\Users\\augus\\Desktop\\public"
														+ linha.replace("GET ", "").replace(" HTTP/1.1", "").replace("/", "\\");

												File file = new File(path);

												if (file.exists()) {
													// Retorna a página solicitada

													FileReader reader = new FileReader(file);
													BufferedReader buffReader = new BufferedReader(reader);

													OUTPUT = "";

													while ((linha = buffReader.readLine()) != null) {
														System.out.println(linha);
														OUTPUT += linha;
													}
													
													reader.close();
													
													//ByteArrayOutputStream
													

													String extensao = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());

													String contentType = "";
													
													System.out.println("\n\nextensão: " + extensao);
													
													switch(extensao.toLowerCase()) {
													
													
													case ".html":
														contentType = "text/html";
														break;
													case ".jpg":
														contentType = "image/jpeg";
														break;
													case ".js":
														contentType = "application/javascript";
														break;
													case ".css":
														contentType = "text/css";
														break;
													case ".gif":
														contentType = "image/gif";
														break;
													case ".jpeg":
														contentType = "image/jpeg";
														break;
													case ".png":
														contentType = "image/png";
														break;
													case ".json":
														contentType = "application/json";
														break;
													default:
														contentType = "text/html";
														break;
													
													}
													
													System.out.println("enviado: " + contentType);
													
													OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" + "Content-Type: " + contentType + "\r\n"
															+ "Content-Length: ";


													BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
															new BufferedOutputStream(cliente.getOutputStream()), "UTF-8"));

													out.write(OUTPUT_HEADERS + OUTPUT.length() + OUTPUT_END_OF_HEADERS
															+ OUTPUT);

													out.flush();
													// out.close();

													// cliente.close();

												} else {

													FileInputStream fileInput = new FileInputStream("C:\\Users\\augus\\Desktop\\public\\404.html");
													InputStreamReader reader = new InputStreamReader(fileInput, "UTF-8");
													BufferedReader buffReader = new BufferedReader(reader);

													OUTPUT = "";

													while ((linha = buffReader.readLine()) != null) {
														System.out.println(linha);
														OUTPUT += linha;
													}
													reader.close();

													BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
															new BufferedOutputStream(cliente.getOutputStream()), "UTF-8"));

													out.write(OUTPUT_HEADERS.replace("200 OK", "404 Nao encontrado") + OUTPUT.length() + OUTPUT_END_OF_HEADERS
															+ OUTPUT);

													out.flush();
													// out.close();
													// cliente.close();
												}
												
												

												System.out.println("Sending...");

												System.out.println("linha: " + linha);

											}
											
											
											
										}
										
										
										
										
										
										//HEAD
										
										else if (line.startsWith("HEAD")) {
											linha = line;
											if (linha != null) {

												// String path = "C:/public" + linha.replace("GET ", "").replace("
												// HTTP/1.1",
												// "");
												String path = "C:\\Users\\x481822\\Downloads\\Entrega 04-20190424T133452Z-001\\Entrega 04\\public\\casa\\teste.html"
														+ linha.replace("HEAD ", "").replace(" HTTP/1.1", "");

												File file = new File(path);

												if (file.exists()) {
													// Retorna a página solicitada

																									

													BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
															new BufferedOutputStream(cliente.getOutputStream()), "UTF-8"));

													out.write(OUTPUT_HEADERS + "0" + OUTPUT_END_OF_HEADERS);

													out.flush();

												} else {

																							

													BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
															new BufferedOutputStream(cliente.getOutputStream()), "UTF-8"));

													out.write(OUTPUT_HEADERS.replace("200 OK", "404 Not Found") + "0" + OUTPUT_END_OF_HEADERS);

													out.flush();
												}

												System.out.println("Sending...");

												System.out.println("linha: " + linha);

											}
											
											
											
										}

										
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
						requisicaoThread.start();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		});
		
		
		servidorThread.start();
	}
		

}