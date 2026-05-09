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
           '2026-05-15',
           '2026-05-16',
           'PER_DAY',
           240,
           4.8,
           7,
           true,
           5,
           'Refunds are available up to 7 days before the event starts.',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO event_images (event_id, image_url)
VALUES
    (1, 'https://ik.imgkit.net/3vlqs5axxjf/TW-Asia/ik-seo/uploadedImages/Features/Cover_Story/17S0856-241115/This-amazing-food-festival-will-let-you-feast-your.jpg?tr=w-780%2Ch-440%2Cfo-auto'),
    (1, 'https://sustainablefoodtrust.org/wp-content/uploads/2023/05/Copy-of-New-website-image-size-34.jpg'),
    (1, 'https://amuse-bouche.pt/wp-content/uploads/2021/04/Lisboafood_.jpg'),
    (1, 'https://blog.italotreno.com/wp-content/uploads/2025/05/summer-food-festivals-italy.jpg');

INSERT INTO event_daily_capacities (event_id, date, capacity)
VALUES
    (1, '2026-08-10', 80),
    (1, '2026-08-11', 100),
    (1, '2026-08-12', 60);