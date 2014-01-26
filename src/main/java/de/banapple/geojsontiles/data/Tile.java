package de.banapple.geojsontiles.data;

public class Tile
{
    public int z;
    public int x;
    public int y;
    
    public Tile(int z, int x, int y)
    {
        this.z = z;
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Tile other = (Tile) obj;
        if ( x != other.x )
            return false;
        if ( y != other.y )
            return false;
        if ( z != other.z )
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        return z + "/" + x + "/" + y;
    }
}
