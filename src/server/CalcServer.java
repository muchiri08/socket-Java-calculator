package server;

import protocol.Request;
import protocol.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class CalcServer {
    // Run this to start the server
    void main() {
        try (var serverSocket = new ServerSocket(8350)) {
            IO.println("server started at port 8350");
            while(true) {
                var clientSocket = serverSocket.accept();
                IO.println("New Connection!");
                var in = new ObjectInputStream(clientSocket.getInputStream());
                var req = (Request) in.readObject();
                IO.println(req);
                var resp = switch (req.operator()) {
                    case "+" -> Response.ok(req.num1() + req.num2());
                    case "-" -> Response.ok(req.num1() - req.num2());
                    case "*" -> Response.ok(req.num1() * req.num2());
                    case "/" -> {
                        if (req.num2() != 0) {
                            yield Response.ok(req.num1() / req.num2());
                        } else {
                            yield Response.invalid();
                        }
                    }
                    case "^" -> Response.ok(Math.pow(req.num1(), req.num2()));
                    case "sqrt" -> {
                        if (req.num1() >= 0) {
                            yield Response.ok(Math.sqrt(req.num1()));
                        } else {
                            yield Response.invalid();
                        }
                    }
                    default -> Response.unsupported();
                };
                var out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(resp);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
