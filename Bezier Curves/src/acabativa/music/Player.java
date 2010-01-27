package acabativa.music;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Player {
	Synthesizer synthesizer = null;
	MidiChannel channel = null;
	int defaultVelocity = 600;
		
	public void initialize(){
		try {
			if (synthesizer == null) {
				if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
					throw new IllegalStateException("Can t initialize synthesizer");
				}
				channel = synthesizer.getChannels()[0];
			}
			synthesizer.open();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void play(int note){
		channel.noteOn(note, defaultVelocity);
	}
	
	public void stop(int note){
		channel.noteOn(note, defaultVelocity);
	}
	
	public void endNotes(){
		channel.allNotesOff();
	}
	
	public void close(){
		try{
			synthesizer.close();
			synthesizer = null;
			channel = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
}
