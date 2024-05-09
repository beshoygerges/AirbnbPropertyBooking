INSERT INTO "user" (uuid, created_at, updated_at, name, email, mobile)
VALUES
    ('8cfa68a3-34cf-4da5-aa2f-88c49c31a688', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'John Doe', 'john@example.com', '1234567890'),
    ('e1c40b79-97cb-48e7-a03b-7f07725b0e86', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Alice Smith', 'alice@example.com', '9876543210'),
    ('452b2309-792f-4d94-91fd-6b374075f3f2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Bob Johnson', 'bob@example.com', '5551234567');

INSERT INTO property (uuid, created_at, updated_at, address, description, rooms, price_per_night)
VALUES
    ('8cfa68a3-34cf-4da5-aa2f-88c49c31a689', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123 Main St', 'Cozy apartment near downtown', 2, 100),
    ('e1c40b79-97cb-48e7-a03b-7f07725b0e87', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '456 Elm St', 'Spacious house with garden', 4, 200),
    ('452b2309-792f-4d94-91fd-6b374075f3f3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '789 Oak St', 'Luxurious villa with pool', 6, 500);

INSERT INTO payment_method (uuid, created_at, updated_at, card_number, cvc, expiry_date, last4, user_id)
VALUES
    ('8cfa68a3-34cf-4da5-aa2f-88c49c31a681', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'WiII7YOuZcTMLan1EpTFhCpBH/aYsBgEQBiSswQkJrM=', '1ETCdEgTw38MEGEAeUUC1g==', 'LiePk3P9OkcXhvsM5/TR5Q==', '1111', 1),
    ('e1c40b79-97cb-48e7-a03b-7f07725b0e81', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'WiII7YOuZcTMLan1EpTFhCpBH/aYsBgEQBiSswQkJrM=', '1ETCdEgTw38MEGEAeUUC1g==', 'LiePk3P9OkcXhvsM5/TR5Q==', '1111', 2),
    ('452b2309-792f-4d94-91fd-6b374075f3f1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'WiII7YOuZcTMLan1EpTFhCpBH/aYsBgEQBiSswQkJrM=', '1ETCdEgTw38MEGEAeUUC1g==', 'LiePk3P9OkcXhvsM5/TR5Q==', '1111', 3);


