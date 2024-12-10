document.getElementById('calculate-salary-btn').addEventListener('click', function () {
    const employeeId = 1; // ID nhân viên (có thể thay bằng dynamic)
    const month = document.getElementById('month').value;
    const year = document.getElementById('year').value;

    if (!month || !year) {
        alert('Vui lòng nhập tháng và năm!');
        return;
    }

    fetch(`/api/salary/monthly?employeeId=${employeeId}&month=${month}&year=${year}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('monthly-salary').innerText = data.toLocaleString() + " VND";
        })
        .catch(error => {
            console.error('Lỗi khi tính lương:', error);
            alert('Không thể tính lương, vui lòng thử lại!');
        });
});
