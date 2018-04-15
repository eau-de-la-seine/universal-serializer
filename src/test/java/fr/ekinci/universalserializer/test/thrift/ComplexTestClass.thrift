namespace java fr.ekinci.universalserializer.test.thrift

/**
 * Command like
 *      thrift -r -out generated --gen java /path/to/service.thrift
 */
struct ThriftComplexTestClass {
	// Primitive attributes + String
	1: byte attr1;
	2: i16 attr2;
	3: i32 attr3;
	4: i64 attr4;
	5: double attr5;
	6: double attr6;
	7: bool attr7;
	8: string attr8;
	9: string attr9;

	// Complex types (NOT for flat files serialization)
	10: list<double> attr10;
	11: list<string> attr11;
	12: ThriftComplexTestClass attr12;
	13: list<ThriftComplexTestClass> attr13;
}
