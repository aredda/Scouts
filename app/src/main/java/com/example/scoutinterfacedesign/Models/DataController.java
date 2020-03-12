package com.example.scoutinterfacedesign.Models;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.Norms.EMission;
import com.example.scoutinterfacedesign.Models.Norms.EUnitType;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.Staff.Member;
import com.example.scoutinterfacedesign.Models.Staff.Person;
import com.example.scoutinterfacedesign.Models.Staff.User;
import com.example.scoutinterfacedesign.Models.System.Arrangement;
import com.example.scoutinterfacedesign.Models.System.Group;
import com.example.scoutinterfacedesign.Models.System.Unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataController implements Serializable
{
    // Database file
    private static String databaseFile = "db.dt";

    // Singleton
    private static DataController CONTROLLER = null;

    // Entity Sets
    public List<Arrangement> Arrangements;
    public List<Person> Staff;
    public List<User> Users;

    // Helper for getting a singleton
    public static DataController get()
    {
        if (CONTROLLER != null)
            return CONTROLLER;

        CONTROLLER = new DataController();

        if (CONTROLLER.Staff.size() == 0)
        {
            CONTROLLER.Staff.add(new Leader (1, "جوزيف", "جوستار", EGender.Male));
            CONTROLLER.Staff.add(new Leader (2, "جوتارو", "جوستار", EGender.Male));
            CONTROLLER.Staff.add(new Leader (3, "جوناثان", "جوستار", EGender.Male));
        }

        return CONTROLLER;
    }

    // Helpers
    public Group findGroup(int id) {
        for (Group g : getArrangements(Group.class))
            if (g.id == id)
                return g;

        return null;
    }

    public List<Unit> getUnits(Group group) {
        List<Unit> r = new ArrayList<>();
        for (Unit u : getArrangements(Unit.class))
            if (u.group.id == group.id)
                r.add(u);

        return r;
    }

    public Unit findUnit(int unitId) {
        for (Unit u : getArrangements(Unit.class))
            if (u.id == unitId)
                return u;

        return null;
    }

    public Member findMember(int memberId) {
        for (Member m : getStaff(Member.class))
            if (m.id == memberId)
                return m;
        return null;
    }

    public <T extends Person> T findPerson(Class<T> type, int id) {
        for (T p : getStaff(type))
            if (p.id == id)
                return p;

        return null;
    }

    /// Generic arrangement getter
    public <T extends Arrangement> List<T> getArrangements(Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Arrangement a : Arrangements)
            if (a.getClass().equals(type))
                result.add((T) a);

        return result;
    }

    /// Generic staff getter
    public ArrayList<Member> getStaff()
    {
        ArrayList<Member> members = new ArrayList<>();

        for ( Person p : Staff )
            members.add( (Member)p );

        return members;
    }
    public <T extends Person> List<T> getStaff(Class<T> type) {
        ArrayList<T> results = new ArrayList<>();
        for (Person p : Staff)
            if (p.getClass().equals(type))
                results.add((T) p);

        return results;
    }

    public List<Leader> getFreeLeaders()
    {
        ArrayList<Leader> leaders = new ArrayList<>();
        for ( Leader l : getStaff(Leader.class) )
            if ( l.currentMission == null )
                leaders.add(l);

        return leaders;
    }

    /// Title
    public String getTitle(EUnitType type, EGender gender) {
        switch (type) {
            case UT1:
                if (gender == EGender.Male)
                    return "شبل";
                return "زهرة";
            case UT2:
                if (gender == EGender.Male)
                    return "كشاف";
                return "مرشدة";
            case UT3:
                if (gender == EGender.Male)
                    return "كشاف متقدم";
        }
        return "رائدة";
    }

    // Authentication
    public User findUser(String username) {
        for (User u : Users)
            if (u.username.compareTo(username) == 0)
                return u;

        return null;
    }

    public DataController()
    {
        // Initialize settings
        Arrangements = new ArrayList<>();
        Staff = new ArrayList<>();
        Users = new ArrayList<>();

        Users.add(new User("admin","admin"));
    }

    public <T extends Enum> List<T> getEnumList(Class<T> t) {
        ArrayList<T> result = new ArrayList<>();

        for (T i : t.getEnumConstants())
            result.add(i);

        return result;
    }

    // Save
    public static void save(Context context) throws Exception
    {
        File fileStream = new File(context.getFilesDir(), databaseFile);
        FileOutputStream fileOutStream = new FileOutputStream(fileStream);
        ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream);

        outStream.writeObject(CONTROLLER);

        outStream.close();
        fileOutStream.close();
    }

    // Load
    public static void load(Context context) throws Exception
    {
        File fileStream = new File(context.getFilesDir(), databaseFile);

        FileInputStream fileInStream = new FileInputStream(fileStream);
        ObjectInputStream inStream = new ObjectInputStream(fileInStream);

        DataController controller = (DataController) inStream.readObject();

        inStream.close();
        fileInStream.close();

        CONTROLLER = controller;
    }
}
