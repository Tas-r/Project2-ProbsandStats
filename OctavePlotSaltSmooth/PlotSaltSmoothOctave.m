% Clear workspace and close figures
clear all;
close all;

% Define the range for x
startingX = -5; % Example starting point
endX = 5;      % Example ending point
step = 1;      % Step size for discrete points

% Step 1: Generate original data (y = x^3)
x = startingX:step:endX;
y = x.^3; % Element-wise cubic function
original_data = [x; y]'; % Store as [x, y] matrix

% Export original data to CSV
fileID = fopen('PolynomialGraph.csv', 'w');
fprintf(fileID, 'x,y\n');
for i = 1:length(x)
    fprintf(fileID, '%d,%d\n', x(i), y(i));
end
fclose(fileID);
disp('Original data exported to PolynomialGraph.csv');

% Step 2: Salt the data (add random noise)
salt_min = -100; % Salt range min
salt_max = 100;  % Salt range max
y_salted = y + (salt_min + (salt_max - salt_min) * rand(1, length(y))); % Add random noise
y_salted = round(y_salted); % Round to integers
salted_data = [x; y_salted]';

% Export salted data to CSV
fileID = fopen('SaltedData.csv', 'w');
fprintf(fileID, 'x,y\n');
for i = 1:length(x)
    fprintf(fileID, '%d,%d\n', x(i), y_salted(i));
end
fclose(fileID);
disp('Salted data exported to SaltedData.csv');

% Step 3: Smooth the salted data (moving average, window size 5)
window_size = 5; % 2 points before, current, 2 points after
half_window = floor(window_size / 2);
y_smoothed = zeros(size(y_salted));
for i = 1:length(y_salted)
    start_idx = max(1, i - half_window);
    end_idx = min(length(y_salted), i + half_window);
    window = y_salted(start_idx:end_idx);
    y_smoothed(i) = round(mean(window)); % Average and round to integer
end
smoothed_data = [x; y_smoothed]';

% Export smoothed data to CSV
fileID = fopen('SmoothedData.csv', 'w');
fprintf(fileID, 'x,y\n');
for i = 1:length(x)
    fprintf(fileID, '%d,%d\n', x(i), y_smoothed(i));
end
fclose(fileID);
disp('Smoothed data exported to SmoothedData.csv');

% Step 4: Plotting
% Plot 1: Scatter plot with lines for salted and smoothed data
figure;
% Scatter points
scatter(x, y, 'r', 'filled', 'DisplayName', 'Original (y = x^3)'); % Red filled circles
hold on;
scatter(x, y_salted, 'g', 'filled', 'DisplayName', 'Salted'); % Green filled circles
scatter(x, y_smoothed, 'b', 'filled', 'DisplayName', 'Smoothed'); % Blue filled circles
% Lines connecting points
plot(x, y_salted, 'g-', 'LineWidth', 1, 'DisplayName', 'Salted Line'); % Green line for salted
plot(x, y_smoothed, 'b-', 'LineWidth', 1, 'DisplayName', 'Smoothed Line'); % Blue line for smoothed
% Continuous smoother for original y = x^3
x_fine = startingX:0.1:endX;
y_fine = x_fine.^3;
plot(x_fine, y_fine, 'k-', 'LineWidth', 2, 'DisplayName', 'y = x^3 (Continuous)');
xlabel('x');
ylabel('y');
title('Original, Salted, and Smoothed Data with Lines');
legend('show');
grid on;
hold off;

% Plot 2: Bar chart (original data)
figure;
bar(x, y, 0.4, 'facecolor', [0, 0.5, 1], 'DisplayName', 'y = x^3'); % Blue bars
xlabel('x');
ylabel('y = x^3');
title('Bar Chart of y = x^3');
legend('show');
grid on;
