INSERT INTO tickets (
    user_id,
    event_id,
    quantity,
    ticket_mode,
    status,
    validation_token,
    price_paid,
    currency,
    created_at
)
VALUES
    (
        1,
        1,
        2,
        'PER_DAY',
        'ACTIVE',
        'test-token-ticket-1',
        5.00,
        'EUR',
        CURRENT_TIMESTAMP
    ),
    (
        1,
        1,
        1,
        'EVENT_PASS',
        'ACTIVE',
        'test-token-ticket-2',
        25.00,
        'EUR',
        CURRENT_TIMESTAMP
    );

INSERT INTO ticket_selected_days (
    ticket_id,
    selected_day
)
VALUES
    (1, '2026-08-10'),
    (1, '2026-08-11');