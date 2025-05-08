public class GrosseZahl implements IGrosseZahl{
    private int[] ziffern;

    public GrosseZahl(String d) {
        ziffern = new int[d.length()];
        for (int i = 0; i < d.length(); i++) {
            ziffern[i] = d.charAt(i) - '0';
        }
    }

    public GrosseZahl(int i) {
        this(String.valueOf(i));
    }
    
    private GrosseZahl(GrosseZahl b)
    {
    	this.ziffern = new int[b.ziffern.length];
    	for(int i = 0; i < this.ziffern.length; i++)
    	{
    		this.ziffern[i] = b.ziffern[i];
    	}
    }
    
    @Override
    public String toString() {
    	String res = "";
    	for(int i = 0; i < this.ziffern.length; i++)
    	{
    		res += this.ziffern[i];
    	}
    	return res;
    }
	
    public boolean less(GrosseZahl b) {
    	
    	GrosseZahl newZ;
    	
        if(this.ziffern.length < b.ziffern.length)
        {
        	newZ = this.fillWithZeros(b);
        	for(int i = 0; i < newZ.ziffern.length; i++)
            {
            	if(newZ.ziffern[i] < b.ziffern[i])
            	{
            		return true;
            	}
            	else if(newZ.ziffern[i] > b.ziffern[i])
            	{
            		return false;
            	}
            }
        	return false;
        }
        
        else
        {
        	newZ = b.fillWithZeros(this);
        	for(int i = 0; i < newZ.ziffern.length; i++)
            {
            	if(this.ziffern[i] < newZ.ziffern[i])
            	{
            		return true;
            	}
            	else if(this.ziffern[i] > newZ.ziffern[i])
            	{
            		return false;
            	}
            }
        	return false;
        	
        }
        
        
        
        
    }

    public GrosseZahl add(GrosseZahl b) {
    	
    	StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = this.ziffern.length - 1;
        int j = b.ziffern.length - 1;

        while (i >= 0 || j >= 0 || carry > 0) 
        {
            int sum = carry;
            if (i >= 0) sum += this.ziffern[i--];
            if (j >= 0) sum += b.ziffern[j--];
            result.append(sum % 10);
            carry = sum / 10;
        }
        
        return new GrosseZahl(result.reverse().toString());
    }

    public GrosseZahl mult(GrosseZahl b) 
    {
        GrosseZahl result = new GrosseZahl(0);
        GrosseZahl z1 = this.reverseZahl();
        GrosseZahl z2 = b.reverseZahl();
        
        for(int i = 0; i < z2.ziffern.length; i++)
        {
        	for(int j = 0; j < z1.ziffern.length; j++)
        	{
        		int fac1 = (int) Math.pow(10, i);
        		int fac2 = (int) Math.pow(10, j);
        		
        		result = result.add(new GrosseZahl(z1.ziffern[j] * fac1 * z2.ziffern[i] * fac2));
        	}
        }
        return result;
    }

    public GrosseZahl subtract(GrosseZahl b)
    {
    	
    	if(this.less(b))
    	{
    		return b.subtract(this);
    	}
    	
    	GrosseZahl z1 = new GrosseZahl(this.reverseZahl());
    	GrosseZahl z2 = new GrosseZahl(b.reverseZahl());
    	
    	for(int i = 0; i < z2.ziffern.length; i++)
    	{
    		z1.ziffern[i] -= z2.ziffern[i];
    		for(int j = 0; j < z1.ziffern.length; j++)
    		{
    			if(z1.ziffern[j] < 0)
        		{
        			z1.ziffern[j+1]--;
        			z1.ziffern[j] += 10;
        		}
    		}
    	}
    	
    	GrosseZahl result = z1.reverseZahl();
    	System.out.println(this.toString() + " - " + b.toString() + " = " + result.toString());
    	return result.removeZeros();
    	
    }
    
    public GrosseZahl ggT(GrosseZahl b)
    {
        GrosseZahl r;
        GrosseZahl resultat;

        if (this.less(b)) {
            return b.ggT(this);
        }

        r = this.subtract(b);
        if (r == null || r.istNull()) {
            resultat = b;
        } else {
            resultat = b.ggT(r);
        }

        return resultat;
    }
    
    private boolean istNull()
    {
    	for(int i = 0; i < this.ziffern.length;i++)
    	{
    		if(this.ziffern[i] != 0)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    private GrosseZahl fillWithZeros(GrosseZahl b)
    {
    	if(this.ziffern.length == b.ziffern.length) return this;
    	StringBuilder n = new StringBuilder(this.toString());
    	for(int i = 0; i < b.ziffern.length - this.ziffern.length; i++)
    	{
    		n.insert(0, '0');
    	}
    	return new GrosseZahl(n.toString());
    }
    
    private GrosseZahl removeZeros()
    {
    	String str = this.toString();
    	for(int i = 0; this.ziffern[i] == 0 && str.length() > 1; i++)
    	{
    		str = str.substring(1);
    	}
    	return new GrosseZahl(str);
    }
    
    private GrosseZahl reverseZahl()
    {
    	GrosseZahl tmp = new GrosseZahl(this);
    	for(int i = 0; i < tmp.ziffern.length / 2; i++)
    	{
    	    int temp = tmp.ziffern[i];
    	    tmp.ziffern[i] = tmp.ziffern[tmp.ziffern.length - i - 1];
    	    tmp.ziffern[tmp.ziffern.length - i - 1] = temp;
    	}
    	return tmp;
    }
    
    public void printZahl()
    {
    	for(int i : ziffern)
    	{
    		System.out.print(i);
    	}
    	System.out.println();
    }
}
