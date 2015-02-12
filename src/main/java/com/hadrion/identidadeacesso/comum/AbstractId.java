
package com.hadrion.identidadeacesso.comum;

import java.io.Serializable;

public abstract class AbstractId
    extends Afirmacao
    implements Identidade, Serializable {

    private static final long serialVersionUID = 1L;
    
    public abstract String id();

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            AbstractId typedObject = (AbstractId) anObject;
            equalObjects = this.id().equals(typedObject.id());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                + (this.hashOddValue() * this.hashPrimeValue())
                + this.id().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return id();
    }

    protected AbstractId(String id) {
        this();

        this.setId(id);
    }

    protected AbstractId() {
        super();
    }

    protected abstract int hashOddValue();

    protected abstract int hashPrimeValue();

    protected abstract void setId(String id);
}
