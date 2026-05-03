INSERT INTO events (
    title,
    description,
    organizer_id,
    status,
    category,
    price,
    currency,
    location,
    start_date,
    end_date,
    ticket_mode,
    capacity,
    average_rating,
    total_reviews,
    refundable,
    refund_deadline_days,
    refund_policy,
    created_at,
    updated_at,
    published_at
)
VALUES (
           'Village Food Festival',
           'Traditional rural food event.',
           2,
           'APPROVED',
           'FOOD',
           5.00,
           'EUR',
           'Bragança',
           '2026-08-10',
           '2026-08-12',
           'PER_DAY',
           240,
           4.8,
           7,
           true,
           7,
           'Refunds are available up to 7 days before the event starts.',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO event_images (event_id, image_url)
VALUES
    (1, 'https://example.com/image1.jpg'),
    (1, 'https://example.com/image2.jpg');

INSERT INTO event_daily_capacities (event_id, date, capacity)
VALUES
    (1, '2026-08-10', 80),
    (1, '2026-08-11', 100),
    (1, '2026-08-12', 60);