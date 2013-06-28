
public class UseSound {

	public static void main(String[] args) {

		//Thread recorder = new Thread(new Recorder());
		//Thread player = new Thread(new Player());
		Graphic graph =	new Graphic(new Recorder(), new Player());
	}
}
