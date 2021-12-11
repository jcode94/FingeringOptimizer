// Finger position optimizer
public class Sheet 
{
    // name
    // composer
    // time signature
    // tempo
    // numMeasures
    private final List<Note> notes;
    private final Map<Note, Integer> optimalFingering;
    private static final NUMFINGERS = 5;

    private Sheet() {}

    Sheet(RawScore score)
    {
        notes = generateMapping(score);
        // get any additional information to populate class fields
        // from rawdata
    }

    private static List<Note> generateMapping(RawScore score)
    {
        List<Note> song = new ArrayList<Note>();
        Note head;
        song.add(head = new Note());
        // parse RawScore and populate sheet object
        for (E e : score)
            song.add(
                new Note(e.octave, e.name, e.accidental, e.length)
            );

        return song;
    }

    public List<Note> generateOptimalFingering()
    {
        if (optimalFingering != null)
            return this.optimalFingering;

        Integer [][] memo = new Integer[notes.size()+1][NUMFINGERS+1]);

        computeOptimalFingering(1, 1, memo);
        return optimalFingering;
    }

    private static int computeOptimalFingering(
        Integer noteIdx, Integer curFinger, Integer [][] optimal
    )
    {
        if (!optimal[noteIdx][curFinger].equals(UNINIT))
            return optimal[noteIdx][curFinger];

        int minCost = (int) 1e9;
        for (int nextFinger = 1; nextFinger <= numFingers; nextFinger++)
        {
            if (nextFinger != curFinger)
            {
                minCost = 
                    Math.min(
                        minCost,
                        computeOptimalFingering(
                            noteIdx + 1, nextFinger, optimal
                        )
                        + difficulty(
                            this.notes.get(noteIdx), curFinger, 
                            this.notes.get(noteIdx + 1), nextFinger)
                    );
            }
        }

        optimal[note][finger] = minCost;
        
        return minCost;
    }

    public int difficulty(Note curNote, int curFinger, Note nextNote, int nextFinger)
    {
        return 0;
    }

    public static void main(String [] args)
    {
    }
}

// This is going to be our internal representation for a Note object
class Note
{
    public final Integer octave;
    public final Integer name;
    public final Integer accidental;
    public final Double length;

    // for this.notes head
    Note() {}

    Note(int o, int n, int a, double l)
    {
        octave = o;
        name = n;
        accidental = a;
        length = l;
    }
}

