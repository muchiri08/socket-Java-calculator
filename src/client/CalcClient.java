package client;

import protocol.Request;
import protocol.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CalcClient {
    // Run this to start the client, input data on the console
    void main() {
        try {
            var oper = IO.readln("Enter your operator: \n");
            var opt1 = Double.valueOf(IO.readln("Enter your first number: \n"));
            var opt2 = switch(oper) {
                case "+", "-", "*", "/", "^" -> Double.valueOf(IO.readln("Enter your second number: \n"));
                default -> null;
            };
            var req = new Request(opt1, opt2, oper);
            var socket = new Socket("localhost", 8350);
            IO.println("Connecting on server");
            var out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(req);
            var in = new ObjectInputStream(socket.getInputStream());
            var resp = (Response) in.readObject();
            IO.println(resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
