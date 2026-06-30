package itu.hatif.util;

import java.util.Objects;

public class UrlMethod {
    
    String methode;
    String url;

    public UrlMethod(String methode, String url) {
        this.methode = methode;
        this.url = url;
    }
    public String getMethode() {
        return methode;
    }
    public void setMethode(String methode) {
        this.methode = methode;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        UrlMethod other = (UrlMethod) obj;

        return other.url.equals(this.url) 
               && other.methode.equals(this.methode);
               
    }

        @Override
    public int hashCode() {
        return Objects.hash(methode, url);
    }
}
