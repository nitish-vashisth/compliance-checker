package com.nitish.compliance.pack;


import java.util.List;

public record RuleExpression(
        RuleExpressionOperator operator,
        String signal,
        String value,
        List<RuleExpression> children
) {

    public static RuleExpression equals(
            String signal,
            String value
    ) {
        return new RuleExpression(
                RuleExpressionOperator.EQUALS,
                signal,
                value,
                List.of()
        );
    }

    public static RuleExpression and(
            List<RuleExpression> children
    ) {
        return new RuleExpression(
                RuleExpressionOperator.AND,
                null,
                null,
                List.copyOf(children)
        );
    }

    public static RuleExpression or(
            List<RuleExpression> children
    ) {
        return new RuleExpression(
                RuleExpressionOperator.OR,
                null,
                null,
                List.copyOf(children)
        );
    }

    public static RuleExpression exists(
            String signal
    ) {
        return new RuleExpression(
                RuleExpressionOperator.EXISTS,
                signal,
                null,
                List.of()
        );
    }

    public static RuleExpression notExists(
            String signal
    ) {
        return new RuleExpression(
                RuleExpressionOperator.NOT_EXISTS,
                signal,
                null,
                List.of()
        );
    }
}