package com.nitish.compliance.engine;


/*
{
  "signal": "data.health_data",
  "operator": "EQUALS",
  "value": "TRUE"
}

Don't add AND, OR, IN, CONTAINS, etc. yet.

We'll introduce composite conditions when we have a concrete requirement that needs them.

* */
public enum RuleOperator {

    EQUALS,

    NOT_EQUALS,

    EXISTS,

    NOT_EXISTS
}