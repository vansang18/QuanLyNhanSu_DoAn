let employeeId = null; // ID nhân viên sẽ được lấy từ API

// Lấy ID của người dùng đang đăng nhập
function fetchEmployeeId() {
    fetch('/api/user/me')
        .then(response => response.json())
        .then(data => {
            employeeId = data.id; // Lưu ID nhân viên
            console.log(`ID nhân viên hiện tại: ${employeeId}`);
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin người dùng:', error);
            alert('Không thể xác định ID người dùng!');
        });
}

// Gọi hàm fetchEmployeeId khi tải trang
fetchEmployeeId();

// Điểm danh hôm nay
document.getElementById('checkin-btn').addEventListener('click', function () {
    if (!employeeId) {
        alert('Không thể thực hiện điểm danh, vui lòng thử lại!');
        return;
    }

    fetch(`/api/attendance/checkin?employeeId=${employeeId}`, {
        method: 'POST'
    })
        .then(response => response.text())
        .then(data => {
            document.getElementById('checkin-status').innerText = data;
        })
        .catch(error => {
            console.error('Lỗi khi điểm danh:', error);
            document.getElementById('checkin-status').innerText = 'Lỗi khi điểm danh!';
        });
});

// Lấy tổng số ngày điểm danh
document.getElementById('get-summary-btn').addEventListener('click', function () {
    const month = document.getElementById('month').value;
    const year = document.getElementById('year').value;

    if (!month || !year) {
        alert('Vui lòng nhập tháng và năm!');
        return;
    }

    if (!employeeId) {
        alert('Không thể thực hiện, vui lòng thử lại!');
        return;
    }

    // Lấy tổng số ngày trong tháng
    fetch(`/api/attendance/monthly?employeeId=${employeeId}&month=${month}&year=${year}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('monthly-count').innerText = data;
        })
        .catch(error => {
            console.error('Lỗi khi lấy tổng số ngày trong tháng:', error);
        });

    // Lấy tổng số ngày trong năm
    fetch(`/api/attendance/yearly?employeeId=${employeeId}&year=${year}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('yearly-count').innerText = data;
        })
        .catch(error => {
            console.error('Lỗi khi lấy tổng số ngày trong năm:', error);
        });
});
