package com.company.customerinfo.repository.unit.test;

import com.company.customerinfo.model.Supplier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public class MetadataEqualsHashCodeTest {

    @Test
    public void testEqualsAndHashCode() {

        EqualsVerifier.forClass(Supplier.class)
                .suppress(Warning.NULL_FIELDS)
                .usingGetClass()
                .verify();
    }

}
