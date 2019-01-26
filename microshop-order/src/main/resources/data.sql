INSERT INTO orders (id, username, status) VALUES
(1, 'John Doe', 0);

INSERT INTO line_items (id, product_id, price, quantity, order_id) VALUES
(1, '1', 100.0, 1, 1),
(2, '2', 50.0, 1, 1);
