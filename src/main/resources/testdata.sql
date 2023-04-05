INSERT INTO account (id, account_number, balance, currency, last_operation_time, customer_id)
SELECT 
    ROW_NUMBER() OVER () AS id,
    'ACCT' || (ROW_NUMBER() OVER ()) AS account_number,
    (RANDOM() * 10000)::NUMERIC(10, 2) AS balance,
    CASE (RANDOM() * 3)::INT
        WHEN 0 THEN 'USD'
        WHEN 1 THEN 'EUR'
        ELSE 'GBP'
    END AS currency,
    NOW() - (RANDOM() * 365)::INT * INTERVAL '1 day' AS last_operation_time,
    (RANDOM() * 20)::INT + 1 AS customer_id
FROM generate_series(1, 100);
