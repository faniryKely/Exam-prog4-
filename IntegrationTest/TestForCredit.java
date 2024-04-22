-- Test 1: Withdrawal authorized. Sufficient balance.
SELECT credit(1, 50.00, '2024-04-01 12:00:00');

-- Test 2: Error: Insufficient balance to cover the requested amount.
        SELECT credit(2, 100.00, '2024-04-01 12:00:00');

-- Test 3: Withdrawal authorized. Sufficient balance with credit.
SELECT credit(3, 150.00, '2024-04-01 12:00:00');

-- Test 4: Error: Insufficient balance to cover the requested amount even with credit.
SELECT credit(4, 200.00, '2024-04-01 12:00:00');

-- Test 5: Error: Account not found.
        SELECT credit(999, 50.00, '2024-04-01 12:00:00');

-- Test 6: Error: No credit information found.
SELECT credit(5, 50.00, '2024-04-01 12:00:00');
