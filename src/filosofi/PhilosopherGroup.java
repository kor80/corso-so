package filosofi;

import java.util.LinkedList;

public class PhilosopherGroup
{
    private LinkedList<Integer> group;

    public PhilosopherGroup(){
        group=new LinkedList<>();
    }

    public PhilosopherGroup( PhilosopherGroup pg ){
        this.group=new LinkedList<>();
        this.group.addAll(pg.group);
    }

    public void addPhilosopher( int philosopherID ){
        group.add(philosopherID);
    }

    public void remove( int philosopherID ){
        group.remove(Integer.valueOf(philosopherID));
    }//remove

    public int hashCode(){
        int M=43;
        int s=0;
        for( int id : group ) s+=id;
        return M*s;
    }//hashCode

    public boolean equals( Object o ){
        if( !(o instanceof PhilosopherGroup) ) return false;
        if( o==this ) return true;
        PhilosopherGroup pg=(PhilosopherGroup)o;
        if( group.size()!=pg.group.size() ) return false;
        if( pg.group.size()==0 ) return true;
        if( pg.group.size()==1 ) return this.group.get(0)==pg.group.get(0);
        if( this.group.get(0)==pg.group.get(0) && this.group.get(1)==pg.group.get(1) ) return true;
        return this.group.get(0)==pg.group.get(1) && this.group.get(1)==pg.group.get(0);
    }//equals

    public String toString(){
        StringBuilder sb=new StringBuilder(group.size()*2+1);
        sb.append("[");
        for( int id : group ){
            sb.append(id);
            sb.append(",");
        }
        if( sb.length()>1 ) sb.setLength(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }//toString
}//PhilosopherGroup
