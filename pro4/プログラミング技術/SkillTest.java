
import org.junit.Test;
import static org.junit.Assert.*;
public class SkillTest{
    @Test public void instantiate(){
        Skill s = new Skill(1,"コンソメパンチ",100,1,10);
        assertEquals(1, s.getId());

    }
}

// junit-4.13.2.jar:hamcrestcore-1.3.jar Skill.java SkillTest.java