CREATE TABLE account (
    id                  SERIAL PRIMARY KEY,
    account_number      VARCHAR(255) NOT NULL,
    balance             DECIMAL(10, 2) NOT NULL,
    currency            VARCHAR(255) NOT NULL,
    last_operation_time TIMESTAMP NOT NULL,
    customer_id         INTEGER NOT NULL
);

CREATE OR REPLACE FUNCTION get_accounts(cust_id INTEGER)
RETURNS TABLE (
    id                  INTEGER,
    account_number      VARCHAR(255),
    balance             DECIMAL(10, 2),
    currency            VARCHAR(255),
    last_operation_time TIMESTAMP
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        account.id,
        account.account_number,
        account.balance,
        account.currency,
        account.last_operation_time
    FROM account
    WHERE account.customer_id = cust_id;
END;
$$ LANGUAGE plpgsql;
