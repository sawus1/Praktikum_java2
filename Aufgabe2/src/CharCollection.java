import java.util.ArrayList;
import java.util.List;

public class CharCollection {
	private List<Character> sammlung = new ArrayList<Character>();
	
	public CharCollection(char... cc)
	{
		for(char c : cc)
		{
			sammlung.add(c);
		}
	}
	public CharCollection(String s)
	{
		for(int i = 0; i < s.length(); i++)
		{
			sammlung.add(s.charAt(i));
		}
	}
	public int size()
	{
		return this.sammlung.size();
	}
	public int count(char c)
	{
		int cnt = 0;
		for(Character ch : this.sammlung)
		{
			if(c == ch.charValue())
			{
				cnt++;
			}
		}
		return cnt;
	}
	public int different()
	{
		CharCollection filtered = new CharCollection();
		for(Character ch : sammlung)
		{
			if(!filtered.sammlung.contains(ch))
			{
				filtered.sammlung.add(ch);
			}
		}
		return filtered.size();
		
	}
	public char top()
	{
		char t = 0;
		int tmp = 0;
		for(Character ch : sammlung)
		{
			if(this.count(ch.charValue()) > tmp)
			{
				t = ch.charValue();
				tmp = this.count(ch.charValue());
			}
		}
		return t;
	}
	
	public CharCollection moreThan(int m) {
	    CharCollection result = new CharCollection();
	    for (Character ch : this.sammlung) {
	        if (this.count(ch) > m) {
	            result.sammlung.add(ch);
	        }
	    }
	    return result;
	}

	
	public CharCollection except(CharCollection cc) {
	    CharCollection result = new CharCollection();
	    List<Character> temp = new ArrayList<>(cc.sammlung);
	    for (Character ch : this.sammlung) {
	        if (temp.contains(ch)) {
	            temp.remove(ch);
	        } else {
	            result.sammlung.add(ch);
	        }
	    }
	    return result;
	}

	public boolean isSubset(CharCollection cc) {
	    for (Character ch : cc.sammlung) {
	        if (this.count(ch) < cc.count(ch)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	@Override
	public String toString()
	{
		String str = "(";
		if(this.sammlung.size() != 0)
		{
			for(Character c : sammlung)
			{
				str += c + ", ";
			}
			str = str.substring(0, str.length() - 2);
		}
		str += ")";
		return str;
		
	}

	@Override
	public boolean equals(Object x)
	{
		if(x == null) return false;
		if(x.getClass() == this.getClass())
		{
			CharCollection x2 = (CharCollection)x;
			for(Character ch : this.sammlung)
			{
				if(this.count(ch.charValue()) != x2.count(ch.charValue()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public void printCollection()
	{
		for(Character c : sammlung)
		{
			System.out.print(c);
		}
	}

}
